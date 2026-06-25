CREATE DATABASE IF NOT EXISTS smart_mobility;
USE smart_mobility;

CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    saldo_crediti_bonus DOUBLE DEFAULT 0.0,
    stato ENUM('DA_VERIFICARE', 'ATTIVO', 'SOSPESO', 'BLOCCATO') NOT NULL DEFAULT 'DA_VERIFICARE'
);

CREATE TABLE metodo_pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL UNIQUE,
    token_dati VARCHAR(255) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE promozione (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    codice_alfanumerico VARCHAR(50) NOT NULL,
    valore_sconto DOUBLE NOT NULL,
    data_scadenza DATETIME NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);
