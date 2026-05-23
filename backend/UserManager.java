package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Classe deputata all'accesso ai dati per l'entità Utente.
 */
public class UserManager {
    
    // Le credenziali non sono più hardcoded, verranno popolate dinamicamente
    private static String DB_URL;
    private static String USER;
    private static String PASS;

    // Blocco statico eseguito al momento del caricamento della classe
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

    /**
     * Ottiene una connessione al database.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Verifica se un'email è già registrata nel database.
     * 
     * @param email L'email da verificare.
     * @return true se l'email esiste, false altrimenti.
     */
    public boolean controllaEsistenzaEmail(String email) {
        String query = "SELECT id FROM Utente WHERE email = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Salva un nuovo utente nel database, eseguendo l'hashing della password.
     * 
     * @param utente L'oggetto Utente da salvare.
     * @return true se il salvataggio è andato a buon fine, false altrimenti.
     */
    public boolean salvaUtente(Utente utente) {
        String query = "INSERT INTO Utente (nome, cognome, email, numeroTelefono, password, statoAutenticato) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getCognome());
            stmt.setString(3, utente.getEmail());
            stmt.setString(4, utente.getNumeroTelefono());
            stmt.setString(5, hashPassword(utente.getPassword()));
            stmt.setBoolean(6, utente.isStatoAutenticato());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida le credenziali fornite per l'accesso.
     * 
     * @param email    L'email fornita.
     * @param password La password in chiaro.
     * @return true se le credenziali sono valide, false altrimenti.
     */
    public boolean validaCredenziali(String email, String password) {
        String query = "SELECT id FROM Utente WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Esegue l'hashing della password utilizzando l'algoritmo SHA-256.
     * 
     * @param password La password in chiaro.
     * @return L'hash esadecimale della password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore: Algoritmo SHA-256 non trovato", e);
        }
    }
}
