package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DAO per la gestione dei Metodi di Pagamento nel database.
 * Implementa le operazioni CRUD per la tabella `MetodoPagamento`.
 */
public class MetodoPagamentoDAO {

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
     * Inserisce un nuovo metodo di pagamento associato a un utente.
     * @param idUtente L'id dell'utente.
     * @param mp L'oggetto MetodoPagamento (con carta già mascherata e token).
     * @return true se l'operazione ha successo.
     */
    public boolean inserisciMetodoPagamento(int idUtente, MetodoPagamento mp) {
        String sql = "INSERT INTO MetodoPagamento (idMetodo, idUtente, tipoPagamento, numeroCarta, dataScadenza, intestatario, statoAttivo, tokenGateway) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mp.getIdMetodo());
            ps.setInt(2, idUtente);
            ps.setString(3, mp.getTipoPagamento());
            ps.setString(4, mp.getNumeroCarta());
            ps.setString(5, mp.getDataScadenza());
            ps.setString(6, mp.getIntestatario());
            ps.setBoolean(7, mp.isStatoAttivo());
            ps.setString(8, mp.getTokenGateway());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
