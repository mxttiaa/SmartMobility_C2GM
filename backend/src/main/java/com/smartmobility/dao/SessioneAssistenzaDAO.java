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

    public java.util.List<SessioneAssistenza> readByAccountEmail(String email) {
        java.util.List<SessioneAssistenza> lista = new java.util.ArrayList<>();
        String sql = "SELECT s.* FROM sessione_assistenza s " +
                     "JOIN account a ON s.account_id = a.id " +
                     "WHERE a.email = ? ORDER BY s.istante_avvio DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             pstmt.setString(1, email);
             try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                 while (rs.next()) {
                     SessioneAssistenza s = new SessioneAssistenza(
                         rs.getString("id_sessione"),
                         rs.getString("categoria_problema"),
                         rs.getString("dettagli_preliminari"),
                         rs.getTimestamp("istante_avvio").toLocalDateTime()
                     );
                     s.setStato(com.smartmobility.model.StatoSessione.valueOf(rs.getString("stato")));
                     lista.add(s);
                 }
             }
        } catch (SQLException e) {
            System.err.println("Errore lettura sessioni assistenza: " + e.getMessage());
        }
        return lista;
    }

    public java.util.List<SessioneAssistenza> readAllPending() {
        java.util.List<SessioneAssistenza> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM sessione_assistenza WHERE stato = 'IN_ATTESA' ORDER BY istante_avvio ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = pstmt.executeQuery()) {
             
             while (rs.next()) {
                 SessioneAssistenza s = new SessioneAssistenza(
                     rs.getString("id_sessione"),
                     rs.getString("categoria_problema"),
                     rs.getString("dettagli_preliminari"),
                     rs.getTimestamp("istante_avvio").toLocalDateTime()
                 );
                 s.setStato(com.smartmobility.model.StatoSessione.valueOf(rs.getString("stato")));
                 lista.add(s);
             }
        } catch (SQLException e) {
            System.err.println("Errore lettura sessioni in attesa: " + e.getMessage());
        }
        return lista;
    }
}
