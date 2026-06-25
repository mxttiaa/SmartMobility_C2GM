package com.smartmobility.api;

import com.smartmobility.dao.SessioneAssistenzaDAO;
import com.smartmobility.model.SessioneAssistenza;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class TicketAPI implements HttpHandler {

    private SessioneAssistenzaDAO sessioneDAO;
    private String[] allowedRoles;

    public TicketAPI(String... allowedRoles) {
        this.sessioneDAO = new SessioneAssistenzaDAO();
        this.allowedRoles = allowedRoles;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = SessionManager.extractToken(authHeader);
        SessionManager.SessionData session = SessionManager.getSession(token);

        if (session == null) {
            sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Non autenticato\"}");
            return;
        }

        boolean authorized = false;
        if (allowedRoles != null) {
            for (String role : allowedRoles) {
                if (role.equals(session.getRuolo())) {
                    authorized = true;
                    break;
                }
            }
        }

        if (!authorized) {
            sendResponse(exchange, 403, "{\"status\": \"error\", \"message\": \"Accesso negato\"}");
            return;
        }

        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGetPending(exchange);
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            if (path.endsWith("/assegna")) {
                handleAssegna(exchange, session.getEmail());
            } else if (path.endsWith("/chiudi")) {
                handleChiudi(exchange);
            } else {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Endpoint non trovato\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void handleGetPending(HttpExchange exchange) throws IOException {
        try {
            List<SessioneAssistenza> pending = sessioneDAO.readAllPending();
            
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (int i = 0; i < pending.size(); i++) {
                SessioneAssistenza s = pending.get(i);
                jsonBuilder.append("{")
                           .append("\"idSessione\": \"").append(s.getIdSessione()).append("\", ")
                           .append("\"categoria\": \"").append(s.getCategoriaProblema()).append("\", ")
                           .append("\"dettagli\": \"").append(s.getDettagliPreliminari().replace("\"", "\\\"")).append("\", ")
                           .append("\"data\": \"").append(s.getIstanteAvvio().toString()).append("\"")
                           .append("}");
                if (i < pending.size() - 1) {
                    jsonBuilder.append(", ");
                }
            }
            jsonBuilder.append("]");

            sendResponse(exchange, 200, jsonBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno\"}");
        }
    }

    private void handleAssegna(HttpExchange exchange, String emailOperatore) throws IOException {
        try {
            String requestBody = readBody(exchange);
            String idSessione = extractJsonField(requestBody, "idSessione");

            if (idSessione == null || idSessione.isEmpty()) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"idSessione mancante\"}");
                return;
            }

            // In una implementazione reale dovremmo caricare l'oggetto sessione e cambiare stato usando CommunicationManager
            // Ma per brevità aggiorniamo direttamente via DAO
            sessioneDAO.assegnaOperatore(idSessione, emailOperatore);
            
            // Impostiamo stato a IN_CORSO. Siccome non abbiamo un readById completo, facciamo un update manuale.
            // Creiamo un oggetto SessioneAssistenza fittizio solo per passare l'ID e lo stato a updateStato
            SessioneAssistenza dummy = new SessioneAssistenza(idSessione, "", "", null);
            dummy.avvia(); // IN_CORSO
            sessioneDAO.updateStato(dummy);

            sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Ticket assegnato con successo\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno\"}");
        }
    }

    private void handleChiudi(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readBody(exchange);
            String idSessione = extractJsonField(requestBody, "idSessione");

            if (idSessione == null || idSessione.isEmpty()) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"idSessione mancante\"}");
                return;
            }

            SessioneAssistenza dummy = new SessioneAssistenza(idSessione, "", "", null);
            dummy.termina(); // TERMINATA
            sessioneDAO.updateStato(dummy);

            sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Ticket chiuso con successo\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno\"}");
        }
    }

    private String readBody(HttpExchange exchange) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    private String extractJsonField(String json, String field) {
        String searchStr = "\"" + field + "\"";
        int index = json.indexOf(searchStr);
        if (index == -1) return null;

        int colonIndex = json.indexOf(":", index);
        if (colonIndex == -1) return null;

        int quote1 = json.indexOf("\"", colonIndex);
        if (quote1 == -1) return null;

        int quote2 = json.indexOf("\"", quote1 + 1);
        if (quote2 == -1) return null;

        return json.substring(quote1 + 1, quote2);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
