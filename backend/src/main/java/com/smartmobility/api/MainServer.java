package com.smartmobility.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainServer {
    public static void main(String[] args) {
        try {
            // Avvia il server sulla porta 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // API di autenticazione (pubblica, non richiede token)
            server.createContext("/api/auth", new AuthAPI());
            
            // API di prenotazione (CLIENTE e OPERATORE)
            server.createContext("/api/prenota", new BookingAPI("CLIENTE", "OPERATORE"));
            
            // API delle promozioni (CLIENTE, OPERATORE, AMMINISTRATORE)
            server.createContext("/api/promozione", new PromozioneAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));

            // API per assistenza e segnalazioni (CLIENTE, OPERATORE, AMMINISTRATORE)
            server.createContext("/api/assistenza", new AssistenzaAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));
            server.createContext("/api/segnalazione-guasto", new SegnalazioneGuastoAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));

            // API per gestione ticket (OPERATORE, AMMINISTRATORE)
            server.createContext("/api/ticket", new TicketAPI("OPERATORE", "AMMINISTRATORE"));

            // API per gestione account (AMMINISTRATORE)
            server.createContext("/api/account", new AccountGestioneAPI("AMMINISTRATORE"));

            // API per la terminazione del noleggio (CLIENTE, OPERATORE, AMMINISTRATORE)
            server.createContext("/api/termina", new TerminaNoleggioAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));
            
            // API dei veicoli (CLIENTE, OPERATORE, AMMINISTRATORE)
            server.createContext("/api/veicoli", new VeicoloAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));
            server.createContext("/api/veicoli/dettaglio", new DettaglioVeicoloAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));
            
            // API dello storico noleggi (CLIENTE, OPERATORE)
            server.createContext("/api/storico", new StoricoAPI("CLIENTE", "OPERATORE", "AMMINISTRATORE"));
            
            server.setExecutor(null); // crea un executor di default
            server.start();
            
            System.out.println("Server avviato sulla porta 8080");
            System.out.println("Endpoints registrati:");
            System.out.println("  POST /api/auth/login");
            System.out.println("  POST /api/auth/register");
            System.out.println("  POST /api/auth/logout");
            System.out.println("  POST /api/prenota");
            System.out.println("  GET  /api/veicoli");
            System.out.println("  GET  /api/storico?email=...");
            System.out.println("  POST /api/termina");
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
        }
    }
}
