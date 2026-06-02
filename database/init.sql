CREATE DATABASE IF NOT EXISTS smart_mobility;
USE smart_mobility;

CREATE TABLE IF NOT EXISTS Utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    numeroTelefono VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    statoAutenticato BOOLEAN DEFAULT FALSE,
    ruolo VARCHAR(50) DEFAULT 'UTENTE'
);

INSERT INTO Utente (nome, cognome, email, numeroTelefono, password, statoAutenticato, ruolo) 
VALUES ('Operatore', 'Zootropolis', 'operatore@zootropolis.it', '1234567890', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', false, 'OPERATORE');