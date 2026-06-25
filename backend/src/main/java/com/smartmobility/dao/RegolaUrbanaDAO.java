package com.smartmobility.dao;

import com.smartmobility.model.Posizione;
import com.smartmobility.model.RegolaUrbana;
import com.smartmobility.model.TipoRestrizione;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegolaUrbanaDAO {

    public void save(RegolaUrbana regola, String emailAdmin) {
        String sql = "INSERT INTO regola_urbana (id_regola, account_id, tipo, perimetro, valore_limite_velocita, data_inizio, data_fine) " +
                     "VALUES (?, (SELECT id FROM account WHERE email = ?), ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, regola.getIdRegola());
            pstmt.setString(2, emailAdmin);
            pstmt.setString(3, regola.getTipo().name());
            
            // Nella realtà si serializzerebbe la lista in JSON. Qui usiamo una stringa di mock.
            pstmt.setString(4, "[{mock_perimetro_json}]"); 
            
            pstmt.setInt(5, regola.getValoreLimiteVelocita());
            pstmt.setTimestamp(6, Timestamp.valueOf(regola.getDataInizio()));
            pstmt.setTimestamp(7, Timestamp.valueOf(regola.getDataFine()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio regola urbana: " + e.getMessage());
        }
    }

    public List<RegolaUrbana> getRegoleAttive(LocalDateTime istante) {
        List<RegolaUrbana> list = new ArrayList<>();
        String sql = "SELECT * FROM regola_urbana WHERE data_inizio <= ? AND data_fine >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            Timestamp ts = Timestamp.valueOf(istante);
            pstmt.setTimestamp(1, ts);
            pstmt.setTimestamp(2, ts);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RegolaUrbana r = new RegolaUrbana(
                        rs.getString("id_regola"),
                        TipoRestrizione.valueOf(rs.getString("tipo")),
                        new ArrayList<Posizione>(), // Mock lista vuota
                        rs.getInt("valore_limite_velocita"),
                        rs.getTimestamp("data_inizio").toLocalDateTime(),
                        rs.getTimestamp("data_fine").toLocalDateTime()
                    );
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore lettura regole attive: " + e.getMessage());
        }
        return list;
    }
}
