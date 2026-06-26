package com.smartmobility.api;

import com.smartmobility.dao.AccountDAO;
import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.manager.BookingManager;
import com.smartmobility.model.Account;
import com.smartmobility.model.Veicolo;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BookingAPI implements HttpHandler {

    private BookingManager bookingManager;
    private AccountDAO accountDAO;
    private VeicoloDAO veicoloDAO;
    private String[] allowedRoles;

    public BookingAPI(String... allowedRoles) {
        this.bookingManager = new BookingManager();
        this.accountDAO = new AccountDAO();
        this.veicoloDAO = new VeicoloDAO();
        this.allowedRoles = allowedRoles;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. Headers per supportare richieste dal frontend (CORS)
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
            sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Non autenticato: effettua il login\"}");
            return;
        }

        // Verifica ruolo
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

        // 2. Se il metodo è POST, leggi il corpo della richiesta
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String requestBody;
                try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                     BufferedReader br = new BufferedReader(isr)) {
                    requestBody = br.lines().collect(Collectors.joining("\n"));
                }

                // 3. Estrazione manuale e semplificata dal JSON
                String email = extractJsonField(requestBody, "email");
                String codiceVeicolo = extractJsonField(requestBody, "codiceVeicolo");
                String codicePromozione = extractJsonField(requestBody, "promozione");

                if (email == null || codiceVeicolo == null) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Campi mancanti nel JSON\"}");
                    return;
                }

                // 4. Recuperiamo l'Account e il Veicolo tramite i DAO
                Account account = accountDAO.readByEmail(email);
                Veicolo veicolo = veicoloDAO.readByCodice(codiceVeicolo);

                if (account == null || veicolo == null) {
                    sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Account o veicolo non trovato\"}");
                    return;
                }

                // 5. Avvio del noleggio
                bookingManager.avviaNoleggio(account, veicolo);

                // Se c'è un codice promozionale fornito, lo bruciamo (eliminiamo) dal DB in modo che non sia riutilizzabile.
                // In uno scenario reale potremmo anche legarlo al record noleggio appena creato.
                if (codicePromozione != null && !codicePromozione.isEmpty()) {
                    com.smartmobility.dao.PromozioneDAO promozioneDAO = new com.smartmobility.dao.PromozioneDAO();
                    promozioneDAO.delete(codicePromozione, email);
                }

                // 6. Invia la risposta JSON finale
                sendResponse(exchange, 200, "{\"status\": \"success\", \"message\": \"Noleggio avviato\"}");

            } catch (IllegalStateException e) {
                sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                // 7. Gestisci le eccezioni restituendo HTTP 500
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    // Metodo helper per estrarre valori stringa dal JSON
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

    // Metodo helper per inviare la risposta HTTP
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
