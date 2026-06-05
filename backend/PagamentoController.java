package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller per la gestione delle richieste REST relative ai Metodi di Pagamento.
 */
public class PagamentoController {

    private PagamentoManager pagamentoManager;

    public PagamentoController() {
        this.pagamentoManager = new PagamentoManager();
    }

    public HttpHandler getRegistrazioneHandler() {
        return new RegistraMetodoPagamentoHandler();
    }

    public HttpHandler getVerificaHandler() {
        return new VerificaMetodoPagamentoHandler();
    }

    /**
     * Aggiunge gli header CORS alla risposta HTTP.
     */
    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    /**
     * Handler per l'endpoint /api/pagamenti/registrazione
     */
    class RegistraMetodoPagamentoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = readRequestBody(exchange);

                String idUtenteStr = extractJsonField(requestBody, "idUtente");
                String tipoPagamento = extractJsonField(requestBody, "tipoPagamento");
                String numeroCarta = extractJsonField(requestBody, "numeroCarta");
                String dataScadenza = extractJsonField(requestBody, "dataScadenza");
                String cvv = extractJsonField(requestBody, "cvv");
                String intestatario = extractJsonField(requestBody, "intestatario");

                if (idUtenteStr == null || numeroCarta == null || dataScadenza == null || cvv == null || intestatario == null) {
                    sendResponse(exchange, 400, "{\"errore\":\"Dati obbligatori mancanti\"}");
                    return;
                }

                int idUtente;
                try {
                    idUtente = Integer.parseInt(idUtenteStr);
                } catch (NumberFormatException e) {
                    sendResponse(exchange, 400, "{\"errore\":\"ID Utente non valido\"}");
                    return;
                }

                DatiMetodoPagamento dati = new DatiMetodoPagamento(numeroCarta, dataScadenza, cvv, intestatario, tipoPagamento);

                try {
                    boolean success = pagamentoManager.registraMetodoPagamento(idUtente, dati);
                    if (success) {
                        sendResponse(exchange, 200, "{\"messaggio\":\"Metodo di pagamento memorizzato con successo\"}");
                    } else {
                        sendResponse(exchange, 500, "{\"errore\":\"Errore durante il salvataggio sul database\"}");
                    }
                } catch (IllegalArgumentException e) {
                    sendResponse(exchange, 400, "{\"errore\":\"" + e.getMessage() + "\"}");
                } catch (Exception e) {
                    sendResponse(exchange, 500, "{\"errore\":\"Errore interno del server\"}");
                }

            } else {
                sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso POST\"}");
            }
        }
    }

    /**
     * Handler per GET /api/pagamenti/verifica
     *
     * <p>Verifica (tramite il token di sessione) se l'utente autenticato
     * ha almeno un metodo di pagamento attivo. Risponde con:
     * <ul>
     *   <li>200 {"haMetodo": true}  – metodo presente</li>
     *   <li>200 {"haMetodo": false} – nessun metodo salvato</li>
     *   <li>401 – token mancante o non valido</li>
     * </ul>
     */
    class VerificaMetodoPagamentoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                // Verifica autenticazione
                String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    sendResponse(exchange, 401, "{\"errore\":\"Non autenticato\"}");
                    return;
                }
                String token = authHeader.substring(7);
                Integer userId = SessionManager.getInstance().getUserIdByToken(token);
                if (userId == null) {
                    sendResponse(exchange, 401, "{\"errore\":\"Token non valido o scaduto\"}");
                    return;
                }

                boolean haMetodo = pagamentoManager.utenteHaMetodoPagamento(userId);
                sendResponse(exchange, 200, "{\"haMetodo\": " + haMetodo + "}");
            } else {
                sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso GET\"}");
            }
        }
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    /**
     * Estrae un campo testuale JSON oppure un campo numerico JSON.
     * Gestisce sia {"campo": "valore"} sia {"campo": valore}.
     */
    private String extractJsonField(String json, String field) {
        // Prima prova con stringhe tra virgolette
        String regexStr = "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"";
        Pattern patternStr = Pattern.compile(regexStr);
        Matcher matcherStr = patternStr.matcher(json);
        if (matcherStr.find()) {
            return matcherStr.group(1);
        }
        // Poi prova senza virgolette (per numeri/booleani)
        String regexNum = "\"" + field + "\"\\s*:\\s*([^,\\}]+)";
        Pattern patternNum = Pattern.compile(regexNum);
        Matcher matcherNum = patternNum.matcher(json);
        if (matcherNum.find()) {
            return matcherNum.group(1).trim();
        }
        return null;
    }
}
