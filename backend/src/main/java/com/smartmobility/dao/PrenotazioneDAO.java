package com.smartmobility.dao;

import com.smartmobility.model.Prenotazione;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PrenotazioneDAO {
    
    public void create(Prenotazione prenotazione, String emailAccount, String codiceVeicolo) {
        String sql = "INSERT INTO prenotazione (account_id, veicolo_id, destinazione, istante_creazione, durata_massima, costo_stimato, stato) " +
                     "VALUES ((SELECT id FROM account WHERE email = ?), (SELECT id FROM veicolo WHERE codice_identificativo = ?), ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailAccount);
            pstmt.setString(2, codiceVeicolo);
            pstmt.setString(3, prenotazione.getDestinazione());
            pstmt.setTimestamp(4, Timestamp.valueOf(prenotazione.getIstanteCreazione()));
            pstmt.setInt(5, prenotazione.getDurataMassima());
            pstmt.setDouble(6, prenotazione.getCostoStimato());
            pstmt.setString(7, prenotazione.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio prenotazione: " + e.getMessage());
        }
    }

    public void update(Prenotazione prenotazione) {
        // Usiamo l'istante di creazione come identificativo per semplicità, non avendo un ID nel modello
        String sql = "UPDATE prenotazione SET stato = ?, costo_stimato = ? WHERE istante_creazione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, prenotazione.getStato().name());
            pstmt.setDouble(2, prenotazione.getCostoStimato());
            pstmt.setTimestamp(3, Timestamp.valueOf(prenotazione.getIstanteCreazione()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore aggiornamento prenotazione: " + e.getMessage());
        }
    }
}
