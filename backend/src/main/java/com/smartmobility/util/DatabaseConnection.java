package com.smartmobility.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String CONFIG_FILE = "config.properties";

    public static Connection getConnection() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            
            return DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return null;
        }
    }
}
