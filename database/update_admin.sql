CREATE TABLE segnalazione_supporto (
    id_segnalazione VARCHAR(50) PRIMARY KEY,
    account_id INT NOT NULL, -- Utente che apre la segnalazione
    gestore_id INT, -- Operatore che prende in carico la segnalazione
    descrizione_problema TEXT NOT NULL,
    istante_creazione DATETIME NOT NULL,
    nota_aggiornamento TEXT,
    esito_intervento TEXT,
    stato ENUM('IN_ATTESA', 'IN_CARICO', 'SOSPESA', 'CHIUSA') NOT NULL DEFAULT 'IN_ATTESA',
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (gestore_id) REFERENCES account(id) ON DELETE SET NULL
);

CREATE TABLE segnalazione_guasto (
    id_segnalazione VARCHAR(50) PRIMARY KEY,
    account_id INT NOT NULL, -- Utente che apre il guasto
    veicolo_id INT NOT NULL, -- Veicolo a cui si riferisce
    categoria_guasto VARCHAR(100) NOT NULL,
    descrizione_anomalia TEXT NOT NULL,
    istante_creazione DATETIME NOT NULL,
    stato ENUM('IN_ATTESA', 'IN_CARICO', 'SOSPESA', 'CHIUSA') NOT NULL DEFAULT 'IN_ATTESA',
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (veicolo_id) REFERENCES veicolo(id) ON DELETE CASCADE
);

CREATE TABLE regola_urbana (
    id_regola VARCHAR(50) PRIMARY KEY,
    account_id INT NOT NULL, -- Amministratore che configura la regola
    tipo ENUM('ZTL', 'AREA_MANUTENZIONE', 'LIMITE_VELOCITA') NOT NULL,
    perimetro TEXT NOT NULL, -- Viene salvato come JSON array di coordinate o WKT (Well-Known Text)
    valore_limite_velocita INT,
    data_inizio DATETIME NOT NULL,
    data_fine DATETIME NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE report_statistico (
    id_report VARCHAR(50) PRIMARY KEY,
    account_id INT NOT NULL, -- Amministratore che genera il report
    criteri_analisi TEXT NOT NULL,
    data_generazione DATETIME NOT NULL,
    dati_aggregati JSON, -- Dati serializzati in JSON
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);
