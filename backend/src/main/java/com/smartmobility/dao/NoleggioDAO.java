package com.smartmobility.dao;

import com.smartmobility.model.Noleggio;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class NoleggioDAO {

    public static class StoricoItem {
        public String codiceVeicolo;
        public String dataInizio;
        public String stato;
        
        public StoricoItem(String codiceVeicolo, String dataInizio, String stato) {
            this.codiceVeicolo = codiceVeicolo;
            this.dataInizio = dataInizio;
            this.stato = stato;
        }
    }
    
    public void create(Noleggio noleggio, String emailAccount, String codiceVeicolo) {
        String sql = "INSERT INTO noleggio (account_id, veicolo_id, inizio_noleggio, stato) " +
                     "VALUES ((SELECT id FROM account WHERE email = ?), (SELECT id FROM veicolo WHERE codice_identificativo = ?), ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, emailAccount);
            pstmt.setString(2, codiceVeicolo);
            pstmt.setTimestamp(3, Timestamp.valueOf(noleggio.getInizioNoleggio()));
            pstmt.setString(4, noleggio.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio noleggio: " + e.getMessage());
        }
    }

    public void update(Noleggio noleggio) {
        // Usiamo inizio_noleggio come identificativo univoco (surrogato) in assenza di ID nel dominio
        String sql = "UPDATE noleggio SET fine_noleggio = ?, costo_finale = ?, stato = ? WHERE inizio_noleggio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            if (noleggio.getFineNoleggio() != null) {
                pstmt.setTimestamp(1, Timestamp.valueOf(noleggio.getFineNoleggio()));
            } else {
                pstmt.setNull(1, Types.TIMESTAMP);
            }
            pstmt.setDouble(2, noleggio.getCostoFinale());
            pstmt.setString(3, noleggio.getStato().name());
            pstmt.setTimestamp(4, Timestamp.valueOf(noleggio.getInizioNoleggio()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore aggiornamento noleggio: " + e.getMessage());
        }
    }

    public List<StoricoItem> readByEmail(String email) {
        List<StoricoItem> lista = new ArrayList<>();
        String sql = "SELECT v.codice_identificativo, n.inizio_noleggio, n.stato " +
                     "FROM noleggio n " +
                     "JOIN veicolo v ON n.veicolo_id = v.id " +
                     "JOIN account a ON n.account_id = a.id " +
                     "WHERE a.email = ? ORDER BY n.inizio_noleggio DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             pstmt.setString(1, email);
             try (ResultSet rs = pstmt.executeQuery()) {
                 while (rs.next()) {
                     lista.add(new StoricoItem(
                         rs.getString("codice_identificativo"),
                         rs.getTimestamp("inizio_noleggio").toLocalDateTime().toString(),
                         rs.getString("stato")
                     ));
                 }
             }
        } catch (SQLException e) {
            System.err.println("Errore lettura storico noleggi: " + e.getMessage());
        }
        return lista;
    }
    public Noleggio readActiveByEmailAndVeicolo(String email, String codiceVeicolo) {
        String sql = "SELECT n.* FROM noleggio n " +
                     "JOIN account a ON n.account_id = a.id " +
                     "JOIN veicolo v ON n.veicolo_id = v.id " +
                     "WHERE a.email = ? AND v.codice_identificativo = ? AND n.stato IN ('IN_CORSO', 'IN_PAUSA') " +
                     "ORDER BY n.inizio_noleggio DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            pstmt.setString(2, codiceVeicolo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Noleggio n = new Noleggio(rs.getTimestamp("inizio_noleggio").toLocalDateTime());
                    if (rs.getTimestamp("fine_noleggio") != null) {
                        n.setFineNoleggio(rs.getTimestamp("fine_noleggio").toLocalDateTime());
                    }
                    n.setCostoFinale(rs.getDouble("costo_finale"));
                    n.setStato(com.smartmobility.model.StatoNoleggio.valueOf(rs.getString("stato")));
                    return n;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore ricerca noleggio attivo: " + e.getMessage());
        }
        return null;
    }
}
