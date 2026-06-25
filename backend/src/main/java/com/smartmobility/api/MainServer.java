package com.smartmobility.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainServer {
    public static void main(String[] args) {
        try {
            // Avvia il server sulla porta 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Creiamo un contesto per l'API di prenotazione
            server.createContext("/api/prenota", new BookingAPI());
            
            // Creiamo un contesto per l'API dei veicoli
            server.createContext("/api/veicoli", new VeicoloAPI());
            
            // Creiamo un contesto per l'API dello storico noleggi
            server.createContext("/api/storico", new StoricoAPI());
            
            server.setExecutor(null); // crea un executor di default
            server.start();
            
            System.out.println("Server avviato sulla porta 8080");
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
        }
    }
}
