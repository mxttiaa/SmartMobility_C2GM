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

CREATE TABLE IF NOT EXISTS MetodoPagamento (
    idMetodo VARCHAR(255) PRIMARY KEY,
    idUtente INT NOT NULL,
    tipoPagamento VARCHAR(50) NOT NULL,
    numeroCarta VARCHAR(255) NOT NULL,
    dataScadenza VARCHAR(10) NOT NULL,
    intestatario VARCHAR(255) NOT NULL,
    statoAttivo BOOLEAN DEFAULT TRUE,
    tokenGateway VARCHAR(255),
    FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE
);
