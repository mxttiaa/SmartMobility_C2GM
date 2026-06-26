package com.smartmobility.api;

import com.smartmobility.dao.AccountDAO;
import com.smartmobility.manager.UserManager;
import com.smartmobility.model.Account;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AccountGestioneAPI implements HttpHandler {

    private UserManager userManager;
    private AccountDAO accountDAO;
    private String[] allowedRoles;

    public AccountGestioneAPI(String... allowedRoles) {
        this.userManager = new UserManager();
        this.accountDAO = new AccountDAO();
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
            sendResponse(exchange, 403, "{\"status\": \"error\", \"message\": \"Accesso negato: richiesti privilegi di AMMINISTRATORE\"}");
            return;
        }

        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGetAccount(exchange);
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            if (path.endsWith("/blocca")) {
                handleBloccaAccount(exchange);
            } else {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Endpoint non trovato\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void handleGetAccount(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();
            String email = null;
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length == 2 && "email".equals(pair[0])) {
                        email = java.net.URLDecoder.decode(pair[1], "UTF-8");
                    }
                }
            }

            if (email == null || email.isEmpty()) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametro email mancante\"}");
                return;
            }

            Account account = accountDAO.readByEmail(email);

            if (account == null) {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Account non trovato\"}");
                return;
            }

            String json = "{" +
                    "\"nome\": \"" + account.getNome() + "\", " +
                    "\"cognome\": \"" + account.getCognome() + "\", " +
                    "\"email\": \"" + account.getEmail() + "\", " +
                    "\"ruolo\": \"" + account.getRuolo() + "\", " +
                    "\"stato\": \"" + account.getStato().name() + "\", " +
                    "\"saldoCreditiBonus\": " + account.getSaldoCreditiBonus() +
                    "}";

            sendResponse(exchange, 200, json);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno\"}");
        }
    }

    private void handleBloccaAccount(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readBody(exchange);
            String email = extractJsonField(requestBody, "email");
            String motivazione = extractJsonField(requestBody, "motivazione");

            if (email == null || motivazione == null) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Email e motivazione sono obbligatori\"}");
                return;
            }

            Account account = accountDAO.readByEmail(email);

            if (account == null) {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Account non trovato\"}");
                return;
            }

            // Verifica che non ci siano noleggi in corso (UC-18 flusso alternativo)
            com.smartmobility.dao.NoleggioDAO noleggioDAO = new com.smartmobility.dao.NoleggioDAO();
            java.util.List<com.smartmobility.dao.NoleggioDAO.StoricoItem> storico = noleggioDAO.readByEmail(email);
            for (com.smartmobility.dao.NoleggioDAO.StoricoItem item : storico) {
                if ("IN_CORSO".equals(item.stato) || "IN_PAUSA".equals(item.stato)) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"L'utente ha un noleggio in corso. Terminare forzatamente la corsa prima di bloccare l'account.\"}");
                    return;
                }
            }

            userManager.bloccaProfilo(account, motivazione);

            sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Account bloccato con successo\"}");
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
