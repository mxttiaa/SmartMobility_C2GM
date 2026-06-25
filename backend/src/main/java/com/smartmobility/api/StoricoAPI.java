package com.smartmobility.api;

import com.smartmobility.dao.NoleggioDAO;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class StoricoAPI implements HttpHandler {

    private NoleggioDAO noleggioDAO;
    private String[] allowedRoles;

    public StoricoAPI(String... allowedRoles) {
        this.noleggioDAO = new NoleggioDAO();
        this.allowedRoles = allowedRoles;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
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

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                // Estrarre il parametro email dall'URL query string
                String query = exchange.getRequestURI().getQuery();
                String email = null;
                if (query != null) {
                    for (String param : query.split("&")) {
                        String[] pair = param.split("=");
                        if (pair.length == 2 && "email".equals(pair[0])) {
                            email = URLDecoder.decode(pair[1], "UTF-8");
                        }
                    }
                }

                if (email == null || email.isEmpty()) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametro email mancante\"}");
                    return;
                }

                List<NoleggioDAO.StoricoItem> lista = noleggioDAO.readByEmail(email);

                String jsonElements = lista.stream().map(item ->
                    "{\"codiceVeicolo\": \"" + item.codiceVeicolo + "\", " +
                    "\"dataInizio\": \"" + item.dataInizio + "\", " +
                    "\"stato\": \"" + item.stato + "\"}"
                ).collect(Collectors.joining(", "));

                sendResponse(exchange, 200, "[" + jsonElements + "]");

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
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
