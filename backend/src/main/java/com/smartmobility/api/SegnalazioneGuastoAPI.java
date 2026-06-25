package com.smartmobility.api;

import com.smartmobility.dao.SegnalazioneDAO;
import com.smartmobility.model.SegnalazioneGuasto;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

public class SegnalazioneGuastoAPI implements HttpHandler {

    private SegnalazioneDAO segnalazioneDAO;
    private String[] allowedRoles;

    public SegnalazioneGuastoAPI(String... allowedRoles) {
        this.segnalazioneDAO = new SegnalazioneDAO();
        this.allowedRoles = allowedRoles;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
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

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String requestBody;
                try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                     BufferedReader br = new BufferedReader(isr)) {
                    requestBody = br.lines().collect(Collectors.joining("\n"));
                }

                String categoria = extractJsonField(requestBody, "categoria");
                String descrizione = extractJsonField(requestBody, "descrizione");
                String codiceVeicolo = extractJsonField(requestBody, "codiceVeicolo");

                if (categoria == null || descrizione == null || codiceVeicolo == null) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Campi mancanti\"}");
                    return;
                }

                String idSegnalazione = "GUASTO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                SegnalazioneGuasto guasto = new SegnalazioneGuasto(idSegnalazione, categoria, descrizione, LocalDateTime.now());

                segnalazioneDAO.createGuasto(guasto, session.getEmail(), codiceVeicolo);

                String json = "{\"status\": \"success\", \"message\": \"Segnalazione guasto inviata con successo\", \"id\": \"" + idSegnalazione + "\"}";
                sendResponse(exchange, 201, json);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
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
