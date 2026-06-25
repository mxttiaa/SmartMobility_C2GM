package com.smartmobility.dao;

import com.smartmobility.model.Account;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.StatoAccount;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {

    public void create(Account account) {
        String sql = "INSERT INTO account (nome, cognome, email, password_hash, ruolo, saldo_crediti_bonus, stato) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setString(1, account.getNome());
            pstmt.setString(2, account.getCognome());
            pstmt.setString(3, account.getEmail());
            pstmt.setString(4, account.getPasswordHash());
            pstmt.setString(5, account.getRuolo());
            pstmt.setDouble(6, account.getSaldoCreditiBonus());
            pstmt.setString(7, account.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'account: " + e.getMessage());
        }
    }

    public Account readByEmail(String email) {
        String sql = "SELECT * FROM account WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email")
                    );
                    account.setPasswordHash(rs.getString("password_hash"));
                    account.setRuolo(rs.getString("ruolo"));
                    account.setSaldoCreditiBonus(rs.getDouble("saldo_crediti_bonus"));
                    account.setStato(StatoAccount.valueOf(rs.getString("stato")));
                    return account;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dell'account: " + e.getMessage());
        }
        return null;
    }

    public void update(Account account) {
        String sql = "UPDATE account SET nome = ?, cognome = ?, saldo_crediti_bonus = ?, stato = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, account.getNome());
            pstmt.setString(2, account.getCognome());
            pstmt.setDouble(3, account.getSaldoCreditiBonus());
            pstmt.setString(4, account.getStato().name());
            pstmt.setString(5, account.getEmail());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'account: " + e.getMessage());
        }
    }

    public void saveMetodoPagamento(String emailAccount, MetodoPagamento metodo) {
        String sql = "INSERT INTO metodo_pagamento (account_id, token_dati) VALUES ((SELECT id FROM account WHERE email = ?), ?) ON DUPLICATE KEY UPDATE token_dati = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailAccount);
            pstmt.setString(2, metodo.getTokenDati());
            pstmt.setString(3, metodo.getTokenDati());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio metodo di pagamento: " + e.getMessage());
        }
    }

    public boolean hasMetodoPagamento(String emailAccount) {
        String sql = "SELECT COUNT(*) FROM metodo_pagamento WHERE account_id = (SELECT id FROM account WHERE email = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailAccount);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore verifica metodo di pagamento: " + e.getMessage());
        }
        return false;
    }
}
