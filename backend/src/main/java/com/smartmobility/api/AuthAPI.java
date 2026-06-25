package com.smartmobility.api;

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

public class AuthAPI implements HttpHandler {

    private UserManager userManager;

    public AuthAPI() {
        this.userManager = new UserManager();
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

        String path = exchange.getRequestURI().getPath();

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            String requestBody;
            try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                requestBody = br.lines().collect(Collectors.joining("\n"));
            }

            if (path.endsWith("/login")) {
                handleLogin(exchange, requestBody);
            } else if (path.endsWith("/register")) {
                handleRegister(exchange, requestBody);
            } else if (path.endsWith("/logout")) {
                handleLogout(exchange);
            } else if (path.endsWith("/verify")) {
                handleVerify(exchange, requestBody);
            } else {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Endpoint non trovato\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void handleLogin(HttpExchange exchange, String requestBody) throws IOException {
        try {
            String email = extractJsonField(requestBody, "email");
            String password = extractJsonField(requestBody, "password");

            if (email == null || password == null) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Email e password sono obbligatori\"}");
                return;
            }

            Account account = userManager.verificaCredenziali(email, password);

            if (account == null) {
                sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Credenziali non valide\"}");
                return;
            }

            // Crea sessione server-side
            String token = SessionManager.createSession(account.getEmail(), account.getRuolo());

            String json = "{\"status\": \"success\", "
                    + "\"token\": \"" + token + "\", "
                    + "\"ruolo\": \"" + account.getRuolo() + "\", "
                    + "\"nome\": \"" + account.getNome() + "\", "
                    + "\"cognome\": \"" + account.getCognome() + "\", "
                    + "\"email\": \"" + account.getEmail() + "\"}";

            sendResponse(exchange, 200, json);

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
        }
    }

    private void handleRegister(HttpExchange exchange, String requestBody) throws IOException {
        try {
            String nome = extractJsonField(requestBody, "nome");
            String cognome = extractJsonField(requestBody, "cognome");
            String email = extractJsonField(requestBody, "email");
            String password = extractJsonField(requestBody, "password");

            if (nome == null || cognome == null || email == null || password == null) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Tutti i campi sono obbligatori\"}");
                return;
            }

            if (password.length() < 6) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"La password deve avere almeno 6 caratteri\"}");
                return;
            }

            Account account = userManager.registraAccount(nome, cognome, email, password);

            String json = "{\"status\": \"success\", "
                    + "\"message\": \"Account creato con successo. Verifica la tua email.\", "
                    + "\"email\": \"" + account.getEmail() + "\"}";

            sendResponse(exchange, 201, json);

        } catch (IllegalArgumentException e) {
            sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
        }
    }

    private void handleLogout(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = SessionManager.extractToken(authHeader);
        if (token != null) {
            SessionManager.destroySession(token);
        }
        sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Logout effettuato\"}");
    }

    private void handleVerify(HttpExchange exchange, String requestBody) throws IOException {
        try {
            String email = extractJsonField(requestBody, "email");
            String codice = extractJsonField(requestBody, "codice");

            if (email == null || codice == null) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Email e codice sono obbligatori\"}");
                return;
            }

            // Temporaneamente istanziamo un nuovo AccountDAO (meglio se UserManager avesse un metodo getAccountByEmail)
            // Però UserManager lo nasconde. Usiamo l'hack di provare a login o aggiungiamo find in UserManager?
            // Aggiungiamo un metodo convalidaCodice che prende solo l'email in UserManager?
            // Modifichiamo UserManager.convalidaCodice per prendere (email, codice) è meglio.
            // Però il metodo attuale in UserManager è: public boolean convalidaCodice(Account account, String codice)
            // Ok, usiamo AccountDAO da qui per recuperare l'account? Non è il massimo, aggiorniamo UserManager dopo, o facciamo qui.
            com.smartmobility.dao.AccountDAO accountDAO = new com.smartmobility.dao.AccountDAO();
            Account account = accountDAO.readByEmail(email);

            if (account == null) {
                sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Account non trovato\"}");
                return;
            }

            boolean isValid = userManager.convalidaCodice(account, codice);

            if (isValid) {
                // Auto-login dopo verifica
                String token = SessionManager.createSession(account.getEmail(), account.getRuolo());
                
                String json = "{\"status\": \"success\", "
                        + "\"message\": \"Account verificato con successo\", "
                        + "\"token\": \"" + token + "\", "
                        + "\"ruolo\": \"" + account.getRuolo() + "\", "
                        + "\"nome\": \"" + account.getNome() + "\", "
                        + "\"cognome\": \"" + account.getCognome() + "\", "
                        + "\"email\": \"" + account.getEmail() + "\"}";
                
                sendResponse(exchange, 200, json);
            } else {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Codice di verifica non valido o account gia' attivo\"}");
            }
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
