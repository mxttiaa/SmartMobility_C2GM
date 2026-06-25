package com.smartmobility.dao;

import com.smartmobility.model.SessioneAssistenza;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessioneAssistenzaDAO {

    public void create(SessioneAssistenza sessione, String emailUtente) {
        String sql = "INSERT INTO sessione_assistenza (id_sessione, account_id, categoria_problema, dettagli_preliminari, istante_avvio, stato) " +
                     "VALUES (?, (SELECT id FROM account WHERE email = ?), ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, sessione.getIdSessione());
            pstmt.setString(2, emailUtente);
            pstmt.setString(3, sessione.getCategoriaProblema());
            pstmt.setString(4, sessione.getDettagliPreliminari());
            pstmt.setTimestamp(5, Timestamp.valueOf(sessione.getIstanteAvvio()));
            pstmt.setString(6, sessione.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio sessione assistenza: " + e.getMessage());
        }
    }

    public void updateStato(SessioneAssistenza sessione) {
        String sql = "UPDATE sessione_assistenza SET stato = ? WHERE id_sessione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, sessione.getStato().name());
            pstmt.setString(2, sessione.getIdSessione());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore aggiornamento stato sessione assistenza: " + e.getMessage());
        }
    }

    public void assegnaOperatore(String idSessione, String emailOperatore) {
        String sql = "UPDATE sessione_assistenza SET operatore_id = (SELECT id FROM account WHERE email = ?) WHERE id_sessione = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailOperatore);
            pstmt.setString(2, idSessione);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore assegnazione operatore a sessione: " + e.getMessage());
        }
    }
}
