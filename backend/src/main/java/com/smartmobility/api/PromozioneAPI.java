package com.smartmobility.api;

import com.smartmobility.dao.PromozioneDAO;
import com.smartmobility.model.Promozione;
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

public class PromozioneAPI implements HttpHandler {

    private PromozioneDAO promozioneDAO;
    private String[] allowedRoles;

    public PromozioneAPI(String... allowedRoles) {
        this.promozioneDAO = new PromozioneDAO();
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

        // Verifica Autenticazione server-side (token-based)
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = SessionManager.extractToken(authHeader);
        SessionManager.SessionData session = SessionManager.getSession(token);

        if (session == null) {
            sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Non autenticato: effettua il login\"}");
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
            sendResponse(exchange, 403, "{\"status\": \"error\", \"message\": \"Accesso negato: ruolo non autorizzato\"}");
            return;
        }

        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGetPromozioni(exchange, session.getEmail());
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            if (path.endsWith("/valida")) {
                handleValidaPromozione(exchange, session.getEmail());
            } else {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Endpoint non trovato\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void handleGetPromozioni(HttpExchange exchange, String email) throws IOException {
        try {
            List<Promozione> promozioni = promozioneDAO.readByAccountEmail(email);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (int i = 0; i < promozioni.size(); i++) {
                Promozione p = promozioni.get(i);
                jsonBuilder.append("{")
                           .append("\"codice\": \"").append(p.getCodiceAlfanumerico()).append("\", ")
                           .append("\"valore\": ").append(p.getValoreSconto()).append(", ")
                           .append("\"scadenza\": \"").append(p.getDataScadenza().toString()).append("\", ")
                           .append("\"valida\": ").append(p.isValida())
                           .append("}");
                if (i < promozioni.size() - 1) {
                    jsonBuilder.append(", ");
                }
            }
            jsonBuilder.append("]");

            sendResponse(exchange, 200, jsonBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
        }
    }

    private void handleValidaPromozione(HttpExchange exchange, String email) throws IOException {
        try {
            String requestBody;
            try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                requestBody = br.lines().collect(Collectors.joining("\n"));
            }

            String codice = extractJsonField(requestBody, "codice");
            if (codice == null || codice.isEmpty()) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Codice promozione obbligatorio\"}");
                return;
            }

            Promozione prom = promozioneDAO.readByCodiceAndEmail(codice, email);
            if (prom == null) {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Promozione non trovata o non associata a questo account\"}");
                return;
            }

            if (!prom.isValida()) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Promozione scaduta\"}");
                return;
            }

            String json = "{\"status\": \"success\", "
                    + "\"message\": \"Promozione valida\", "
                    + "\"sconto\": " + prom.getValoreSconto() + "}";
            sendResponse(exchange, 200, json);

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
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
