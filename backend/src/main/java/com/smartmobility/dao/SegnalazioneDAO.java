package com.smartmobility.dao;

import com.smartmobility.model.SegnalazioneGuasto;
import com.smartmobility.model.SegnalazioneSupporto;
import com.smartmobility.model.StatoSegnalazione;
import com.smartmobility.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SegnalazioneDAO {

    public void createSupporto(SegnalazioneSupporto s, String emailAccount) {
        String sql = "INSERT INTO segnalazione_supporto (id_segnalazione, account_id, descrizione_problema, istante_creazione, stato) " +
                     "VALUES (?, (SELECT id FROM account WHERE email = ?), ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, s.getIdSegnalazione());
            pstmt.setString(2, emailAccount);
            pstmt.setString(3, s.getDescrizioneProblema());
            pstmt.setTimestamp(4, Timestamp.valueOf(s.getIstanteCreazione()));
            pstmt.setString(5, s.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio segnalazione supporto: " + e.getMessage());
        }
    }

    public void createGuasto(SegnalazioneGuasto g, String emailAccount, String codiceVeicolo) {
        String sql = "INSERT INTO segnalazione_guasto (id_segnalazione, account_id, veicolo_id, categoria_guasto, descrizione_anomalia, istante_creazione, stato) " +
                     "VALUES (?, (SELECT id FROM account WHERE email = ?), (SELECT id FROM veicolo WHERE codice_identificativo = ?), ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, g.getIdSegnalazione());
            pstmt.setString(2, emailAccount);
            pstmt.setString(3, codiceVeicolo);
            pstmt.setString(4, g.getCategoriaGuasto());
            pstmt.setString(5, g.getDescrizioneAnomalia());
            pstmt.setTimestamp(6, Timestamp.valueOf(g.getIstanteCreazione()));
            pstmt.setString(7, g.getStato().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore salvataggio segnalazione guasto: " + e.getMessage());
        }
    }

    public void updateStatoSupporto(SegnalazioneSupporto s, String emailOperatore) {
        String sql;
        if (emailOperatore != null) {
            sql = "UPDATE segnalazione_supporto SET stato = ?, gestore_id = (SELECT id FROM account WHERE email = ?), nota_aggiornamento = ?, esito_intervento = ? WHERE id_segnalazione = ?";
        } else {
            sql = "UPDATE segnalazione_supporto SET stato = ?, nota_aggiornamento = ?, esito_intervento = ? WHERE id_segnalazione = ?";
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            if (emailOperatore != null) {
                pstmt.setString(1, s.getStato().name());
                pstmt.setString(2, emailOperatore);
                pstmt.setString(3, s.getNotaAggiornamento());
                pstmt.setString(4, s.getEsitoIntervento());
                pstmt.setString(5, s.getIdSegnalazione());
            } else {
                pstmt.setString(1, s.getStato().name());
                pstmt.setString(2, s.getNotaAggiornamento());
                pstmt.setString(3, s.getEsitoIntervento());
                pstmt.setString(4, s.getIdSegnalazione());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore aggiornamento segnalazione supporto: " + e.getMessage());
        }
    }

    public List<SegnalazioneSupporto> getSegnalazioniInAttesa() {
        List<SegnalazioneSupporto> list = new ArrayList<>();
        String sql = "SELECT * FROM segnalazione_supporto WHERE stato = 'IN_ATTESA'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                SegnalazioneSupporto s = new SegnalazioneSupporto(
                    rs.getString("id_segnalazione"),
                    rs.getString("descrizione_problema"),
                    rs.getTimestamp("istante_creazione").toLocalDateTime()
                );
                s.setNotaAggiornamento(rs.getString("nota_aggiornamento"));
                s.setEsitoIntervento(rs.getString("esito_intervento"));
                s.setStato(StatoSegnalazione.valueOf(rs.getString("stato")));
                list.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Errore lettura segnalazioni in attesa: " + e.getMessage());
        }
        return list;
    }
}
