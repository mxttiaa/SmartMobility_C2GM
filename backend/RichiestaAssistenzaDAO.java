package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RichiestaAssistenzaDAO {
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
            System.err.println("Errore: Impossibile leggere config.properties");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public RichiestaAssistenza save(RichiestaAssistenza richiesta) {
        String sql = "INSERT INTO RichiestaAssistenza (idUtente, descrizioneProblema, stato) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, richiesta.getIdUtente());
            ps.setString(2, richiesta.getDescrizioneProblema());
            ps.setString(3, richiesta.getStato());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        richiesta.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return richiesta;
    }

    public RichiestaAssistenza findById(int id) {
        String sql = "SELECT * FROM RichiestaAssistenza WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RichiestaAssistenza(
                            rs.getInt("id"),
                            rs.getInt("idUtente"),
                            rs.getString("descrizioneProblema"),
                            rs.getString("stato"),
                            rs.getTimestamp("dataCreazione"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RichiestaAssistenza> findAllByUtente(int idUtente) {
        List<RichiestaAssistenza> list = new ArrayList<>();
        String sql = "SELECT * FROM RichiestaAssistenza WHERE idUtente = ? ORDER BY dataCreazione DESC";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new RichiestaAssistenza(
                            rs.getInt("id"),
                            rs.getInt("idUtente"),
                            rs.getString("descrizioneProblema"),
                            rs.getString("stato"),
                            rs.getTimestamp("dataCreazione")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStato(int id, String nuovoStato) {
        String sql = "UPDATE RichiestaAssistenza SET stato = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuovoStato);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
