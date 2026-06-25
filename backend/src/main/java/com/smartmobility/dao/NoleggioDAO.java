package com.smartmobility.dao;

import com.smartmobility.model.Noleggio;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class NoleggioDAO {
    
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
}
