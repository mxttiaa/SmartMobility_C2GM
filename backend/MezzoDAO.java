package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

/**
 * DAO per il recupero dei dati del Mezzo dal DB.
 */
public class MezzoDAO {
    private static String DB_URL;
    private static String USER;
    private static String PASS;

    static {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
            DB_URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Errore: Impossibile leggere il file config.properties dalla root del progetto.");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Recupera un mezzo dal database dato il suo ID.
     * @param idMezzo ID del mezzo.
     * @return L'oggetto Mezzo, oppure null se non trovato.
     */
    public Mezzo getMezzoById(int idMezzo) {
        String sql = "SELECT * FROM Mezzo WHERE idMezzo = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMezzo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String stato;
                    try { stato = rs.getString("statoOperativo"); } catch (SQLException e) { stato = "disponibile"; }
                    return new Mezzo(
                        rs.getInt("idMezzo"),
                        rs.getString("tipologia"),
                        rs.getDouble("portataMassima"),
                        rs.getDouble("livelloBatteria"),
                        rs.getDouble("latitudine"),
                        rs.getDouble("longitudine"),
                        stato
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recupera tutti i mezzi dal database.
     * @return Una lista di oggetti Mezzo.
     */
    public List<Mezzo> getAllMezzi() {
        List<Mezzo> mezzi = new ArrayList<>();
        String sql = "SELECT * FROM Mezzo";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String stato;
                try { stato = rs.getString("statoOperativo"); } catch (SQLException e) { stato = "disponibile"; }
                mezzi.add(new Mezzo(
                    rs.getInt("idMezzo"),
                    rs.getString("tipologia"),
                    rs.getDouble("portataMassima"),
                    rs.getDouble("livelloBatteria"),
                    rs.getDouble("latitudine"),
                    rs.getDouble("longitudine"),
                    stato
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mezzi;
    }
}
