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

public class PagamentoAPI implements HttpHandler {

    private UserManager userManager;
    private AccountDAO accountDAO;

    public PagamentoAPI() {
        this.userManager = new UserManager();
        this.accountDAO = new AccountDAO();
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

        // Verifica Autenticazione server-side (token-based)
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = SessionManager.extractToken(authHeader);
        SessionManager.SessionData session = SessionManager.getSession(token);

        if (session == null) {
            sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Non autenticato\"}");
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String requestBody;
                try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                     BufferedReader br = new BufferedReader(isr)) {
                    requestBody = br.lines().collect(Collectors.joining("\n"));
                }

                String datiCarta = extractJsonField(requestBody, "datiCarta");

                if (datiCarta == null || datiCarta.isEmpty()) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Dati carta mancanti\"}");
                    return;
                }

                Account account = accountDAO.readByEmail(session.getEmail());
                if (account == null) {
                    sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Account non trovato\"}");
                    return;
                }

                // Chiama userManager che a sua volta (simula) valida con SistemaPagamento e salva su DB
                userManager.associaPagamento(account, datiCarta);

                sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Metodo di pagamento associato con successo\"}");

            } catch (IllegalArgumentException e) {
                // Errore di validazione (es. dati carta non validi dal Sistema Esterno)
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
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
