CREATE DATABASE IF NOT EXISTS smart_mobility;
USE smart_mobility;

CREATE TABLE IF NOT EXISTS Utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    numeroTelefono VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    statoAutenticato BOOLEAN DEFAULT FALSE
);