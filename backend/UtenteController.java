package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller per la gestione delle richieste REST relative agli Utenti.
 */
public class UtenteController {

    private UserManager userManager;

    public UtenteController() {
        this.userManager = new UserManager();
    }

    /**
     * Avvia il server HTTP per esporre le API.
     */
    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/registrazione", new RegistrazioneHandler());
        server.createContext("/api/accesso", new AccessoHandler());

        PagamentoController pagamentoController = new PagamentoController();
        server.createContext("/api/pagamenti/registrazione", pagamentoController.getRegistrazioneHandler());

        server.createContext("/api/mezzo", new MezzoController());

        server.setExecutor(null); // crea un default executor
        server.start();
        System.out.println("UtenteController: API in ascolto sulla porta 8080...");
    }

    /**
     * Aggiunge gli header CORS alla risposta HTTP per aggirare il blocco del
     * browser.
     */
    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    /**
     * Handler per l'endpoint /api/registrazione
     */
    class RegistrazioneHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);

            // Gestione della richiesta di Preflight
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // 204 No Content
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = readRequestBody(exchange);

                String email = extractJsonField(requestBody, "email");
                String nome = extractJsonField(requestBody, "nome");
                String cognome = extractJsonField(requestBody, "cognome");
                String password = extractJsonField(requestBody, "password");
                String numeroTelefono = extractJsonField(requestBody, "numeroTelefono");

                if (email == null || nome == null || cognome == null || password == null) {
                    sendResponse(exchange, 400, "{\"errore\":\"Dati obbligatori mancanti\"}");
                    return;
                }

                if (userManager.controllaEsistenzaEmail(email)) {
                    sendResponse(exchange, 400, "{\"errore\":\"Email già esistente\"}");
                    return;
                }

                Utente utente = new Utente();
                utente.setNome(nome);
                utente.setCognome(cognome);
                utente.setEmail(email);
                utente.setPassword(password);
                utente.setNumeroTelefono(numeroTelefono);
                utente.setStatoAutenticato(false);

                if (userManager.salvaUtente(utente)) {
                    sendResponse(exchange, 200, "{\"messaggio\":\"Registrazione effettuata con successo\"}");
                } else {
                    sendResponse(exchange, 500, "{\"errore\":\"Errore interno del server durante il salvataggio\"}");
                }
            } else {
                sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso POST\"}");
            }
        }
    }

    /**
     * Handler per l'endpoint /api/accesso
     */
    class AccessoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);

            // Gestione della richiesta di Preflight
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // 204 No Content
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = readRequestBody(exchange);

                String email = extractJsonField(requestBody, "email");
                String password = extractJsonField(requestBody, "password");

                if (email == null || password == null) {
                    sendResponse(exchange, 400, "{\"errore\":\"Dati obbligatori mancanti\"}");
                    return;
                }

                if (userManager.validaCredenziali(email, password)) {
                    sendResponse(exchange, 200, "{\"messaggio\":\"Accesso effettuato con successo\"}");
                } else {
                    sendResponse(exchange, 401, "{\"errore\":\"Credenziali errate\"}");
                }
            } else {
                sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso POST\"}");
            }
        }
    }

    /**
     * Legge il corpo della richiesta in formato stringa.
     */
    private String readRequestBody(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /**
     * Invia la risposta HTTP con il codice di stato e il corpo JSON.
     */
    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    /**
     * Utility basilare per estrarre il valore di un campo stringa da un JSON senza
     * librerie esterne.
     * Funziona per formati del tipo "campo" : "valore".
     */
    private String extractJsonField(String json, String field) {
        String regex = "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            UtenteController controller = new UtenteController();
            controller.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
