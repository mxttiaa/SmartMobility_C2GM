package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DAO per il recupero dei dati delle Tariffe dal DB.
 */
public class TariffaDAO {
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
     * Recupera la tariffa vigente per una certa tipologia di mezzo.
     * 
     * @param tipoMezzo Tipologia del mezzo.
     * @return L'oggetto Tariffa, oppure null se non trovato.
     */
    public Tariffa findByTipoMezzo(String tipoMezzo) {
        String sql = "SELECT * FROM tariffa WHERE tipo_mezzo = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipoMezzo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Tariffa(
                            rs.getInt("id"),
                            rs.getString("tipo_mezzo"),
                            rs.getDouble("costo_base"),
                            rs.getDouble("costo_minuto"),
                            rs.getDouble("costo_km"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
