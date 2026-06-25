package com.smartmobility.api;

import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.model.Automobile;
import com.smartmobility.model.Bicicletta;
import com.smartmobility.model.Monopattino;
import com.smartmobility.model.Veicolo;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DettaglioVeicoloAPI implements HttpHandler {

    private VeicoloDAO veicoloDAO;
    private String[] allowedRoles;

    public DettaglioVeicoloAPI(String... allowedRoles) {
        this.veicoloDAO = new VeicoloDAO();
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
                // Estrarre il parametro codice dall'URL query string
                String query = exchange.getRequestURI().getQuery();
                String codice = null;
                if (query != null) {
                    for (String param : query.split("&")) {
                        String[] pair = param.split("=");
                        if (pair.length == 2 && "codice".equals(pair[0])) {
                            codice = URLDecoder.decode(pair[1], "UTF-8");
                        }
                    }
                }

                if (codice == null || codice.isEmpty()) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametro codice mancante\"}");
                    return;
                }

                Veicolo veicolo = veicoloDAO.readByCodice(codice);

                if (veicolo == null) {
                    sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Veicolo non trovato\"}");
                    return;
                }

                String tipo = "Generico";
                String specifiche = "";
                
                if (veicolo instanceof Automobile) {
                    tipo = "Automobile";
                    Automobile auto = (Automobile) veicolo;
                    specifiche = "\"targa\": \"" + auto.getTarga() + "\", " +
                                 "\"numeroPosti\": " + auto.getNumeroPosti() + ", ";
                } else if (veicolo instanceof Monopattino) {
                    tipo = "Monopattino";
                    Monopattino mono = (Monopattino) veicolo;
                    specifiche = "\"velocitaMassima\": " + mono.getVelocitaMassima() + ", ";
                } else if (veicolo instanceof Bicicletta) {
                    tipo = "Bicicletta";
                    Bicicletta bici = (Bicicletta) veicolo;
                    specifiche = "\"pedalataAssistita\": " + bici.isPedalataAssistita() + ", ";
                }

                String jsonResponse = "{" +
                        "\"codice\": \"" + veicolo.getCodiceIdentificativo() + "\", " +
                        "\"modello\": \"" + tipo + "\", " +
                        specifiche +
                        "\"stato\": \"" + veicolo.getStatoOperativo().name() + "\", " +
                        "\"latitudine\": " + veicolo.getCoordinateAttuali().getLatitudine() + ", " +
                        "\"longitudine\": " + veicolo.getCoordinateAttuali().getLongitudine() + ", " +
                        "\"portataMassima\": " + veicolo.getPortataMassima() + ", " +
                        "\"livelloCarica\": " + veicolo.getLivelloCaricaResidua() + ", " +
                        "\"autonomiaStimata\": " + veicolo.getAutonomiaStimata() + ", " +
                        "\"tipoVeicolo\": \"" + tipo + "\"" +
                        "}";

                sendResponse(exchange, 200, jsonResponse);

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
