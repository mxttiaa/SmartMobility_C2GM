package com.smartmobility.api;

import com.smartmobility.dao.SessioneAssistenzaDAO;
import com.smartmobility.manager.CommunicationManager;
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

public class AssistenzaAPI implements HttpHandler {

    private CommunicationManager communicationManager;
    private SessioneAssistenzaDAO sessioneDAO;
    private String[] allowedRoles;

    public AssistenzaAPI(String... allowedRoles) {
        this.communicationManager = new CommunicationManager();
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

        // Verifica Autenticazione server-side
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

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGetSessioni(exchange, session.getEmail());
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleCreaSessione(exchange, session.getEmail());
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void handleGetSessioni(HttpExchange exchange, String email) throws IOException {
        try {
            List<SessioneAssistenza> sessioni = sessioneDAO.readByAccountEmail(email);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (int i = 0; i < sessioni.size(); i++) {
                SessioneAssistenza s = sessioni.get(i);
                jsonBuilder.append("{")
                           .append("\"idSessione\": \"").append(s.getIdSessione()).append("\", ")
                           .append("\"categoria\": \"").append(s.getCategoriaProblema()).append("\", ")
                           .append("\"dettagli\": \"").append(s.getDettagliPreliminari().replace("\"", "\\\"")).append("\", ")
                           .append("\"stato\": \"").append(s.getStato().name()).append("\", ")
                           .append("\"data\": \"").append(s.getIstanteAvvio().toString()).append("\"")
                           .append("}");
                if (i < sessioni.size() - 1) {
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

    private void handleCreaSessione(HttpExchange exchange, String email) throws IOException {
        try {
            String requestBody;
            try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                requestBody = br.lines().collect(Collectors.joining("\n"));
            }

            String categoria = extractJsonField(requestBody, "categoria");
            String dettagli = extractJsonField(requestBody, "dettagli");

            if (categoria == null || dettagli == null) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Campi mancanti\"}");
                return;
            }

            SessioneAssistenza sessione = communicationManager.richiediAssistenza(email, categoria, dettagli);

            String json = "{\"status\": \"success\", \"message\": \"Richiesta inviata\", \"idSessione\": \"" + sessione.getIdSessione() + "\"}";
            sendResponse(exchange, 201, json);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno\"}");
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
