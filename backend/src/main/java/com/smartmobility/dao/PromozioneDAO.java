package com.smartmobility.dao;

import com.smartmobility.model.Promozione;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PromozioneDAO {

    public List<Promozione> readByAccountEmail(String email) {
        List<Promozione> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM promozione p " +
                     "JOIN account a ON p.account_id = a.id " +
                     "WHERE a.email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             pstmt.setString(1, email);
             try (ResultSet rs = pstmt.executeQuery()) {
                 while (rs.next()) {
                     Promozione prom = new Promozione(
                         rs.getString("codice_alfanumerico"),
                         rs.getDouble("valore_sconto"),
                         rs.getTimestamp("data_scadenza").toLocalDateTime()
                     );
                     lista.add(prom);
                 }
             }
        } catch (SQLException e) {
            System.err.println("Errore lettura promozioni: " + e.getMessage());
        }
        return lista;
    }

    public Promozione readByCodiceAndEmail(String codice, String email) {
        String sql = "SELECT p.* FROM promozione p " +
                     "JOIN account a ON p.account_id = a.id " +
                     "WHERE p.codice_alfanumerico = ? AND a.email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             pstmt.setString(1, codice);
             pstmt.setString(2, email);
             try (ResultSet rs = pstmt.executeQuery()) {
                 if (rs.next()) {
                     return new Promozione(
                         rs.getString("codice_alfanumerico"),
                         rs.getDouble("valore_sconto"),
                         rs.getTimestamp("data_scadenza").toLocalDateTime()
                     );
                 }
             }
        } catch (SQLException e) {
            System.err.println("Errore lettura singola promozione: " + e.getMessage());
        }
        return null;
    }

    public void create(Promozione promozione, String emailAccount) {
        String sql = "INSERT INTO promozione (account_id, codice_alfanumerico, valore_sconto, data_scadenza) " +
                     "VALUES ((SELECT id FROM account WHERE email = ?), ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailAccount);
            pstmt.setString(2, promozione.getCodiceAlfanumerico());
            pstmt.setDouble(3, promozione.getValoreSconto());
            pstmt.setTimestamp(4, Timestamp.valueOf(promozione.getDataScadenza()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio promozione: " + e.getMessage());
        }
    }
    
    public void delete(String codice, String email) {
        String sql = "DELETE p FROM promozione p " +
                     "JOIN account a ON p.account_id = a.id " +
                     "WHERE p.codice_alfanumerico = ? AND a.email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, codice);
            pstmt.setString(2, email);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore eliminazione promozione: " + e.getMessage());
        }
    }
}
