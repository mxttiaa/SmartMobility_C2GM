package com.smartmobility.dao;

import com.smartmobility.model.*;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeicoloDAO {
    
    public Veicolo readByCodice(String codiceIdentificativo) {
        String sql = "SELECT * FROM veicolo WHERE codice_identificativo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, codiceIdentificativo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo_veicolo");
                    Posizione pos = new Posizione(rs.getDouble("latitudine"), rs.getDouble("longitudine"));
                    float carica = rs.getFloat("livello_carica_residua");
                    float portata = rs.getFloat("portata_massima");
                    StatoVeicolo stato = StatoVeicolo.valueOf(rs.getString("stato_operativo"));
                    
                    Veicolo veicolo = null;
                    if ("AUTOMOBILE".equalsIgnoreCase(tipo)) {
                        veicolo = new Automobile(codiceIdentificativo, carica, portata, pos, 
                            rs.getString("targa"), rs.getInt("numero_posti"));
                    } else if ("MONOPATTINO".equalsIgnoreCase(tipo)) {
                        veicolo = new Monopattino(codiceIdentificativo, carica, portata, pos, 
                            rs.getInt("velocita_massima"));
                    } else if ("BICICLETTA".equalsIgnoreCase(tipo)) {
                        veicolo = new Bicicletta(codiceIdentificativo, carica, portata, pos, 
                            rs.getBoolean("pedalata_assistita"));
                    }
                    
                    if (veicolo != null) {
                        veicolo.setStatoOperativo(stato);
                    }
                    return veicolo;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore lettura veicolo: " + e.getMessage());
        }
        return null;
    }

    public void updateStatoEPosizione(Veicolo veicolo) {
        String sql = "UPDATE veicolo SET stato_operativo = ?, latitudine = ?, longitudine = ? WHERE codice_identificativo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, veicolo.getStatoOperativo().name());
            pstmt.setDouble(2, veicolo.getCoordinateAttuali().getLatitudine());
            pstmt.setDouble(3, veicolo.getCoordinateAttuali().getLongitudine());
            pstmt.setString(4, veicolo.getCodiceIdentificativo());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore aggiornamento veicolo: " + e.getMessage());
        }
    }
}
