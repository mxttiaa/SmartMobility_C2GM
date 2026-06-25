CREATE TABLE veicolo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codice_identificativo VARCHAR(50) NOT NULL UNIQUE,
    livello_carica_residua FLOAT NOT NULL,
    portata_massima FLOAT NOT NULL,
    stato_operativo ENUM('DISPONIBILE', 'IN_USO', 'IN_MANUTENZIONE', 'BATTERIA_SCARICA') NOT NULL DEFAULT 'DISPONIBILE',
    latitudine DOUBLE NOT NULL,
    longitudine DOUBLE NOT NULL,
    tipo_veicolo VARCHAR(20) NOT NULL, -- Implementazione Single Table ('AUTOMOBILE', 'MONOPATTINO', 'BICICLETTA')
    
    -- Campi specifici per Automobile
    targa VARCHAR(20),
    numero_posti INT,
    
    -- Campi specifici per Monopattino
    velocita_massima INT,
    
    -- Campi specifici per Bicicletta
    pedalata_assistita BOOLEAN
);

CREATE TABLE tariffa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    costo_sblocco DOUBLE NOT NULL,
    costo_al_minuto DOUBLE NOT NULL,
    tipologia_veicolo VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE prenotazione (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    veicolo_id INT NOT NULL,
    destinazione VARCHAR(255),
    istante_creazione DATETIME NOT NULL,
    durata_massima INT NOT NULL,
    costo_stimato DOUBLE NOT NULL,
    stato ENUM('ATTIVA', 'SCADUTA', 'CONVERTITA') NOT NULL DEFAULT 'ATTIVA',
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (veicolo_id) REFERENCES veicolo(id) ON DELETE CASCADE
);

CREATE TABLE noleggio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    veicolo_id INT NOT NULL,
    prenotazione_id INT, -- Può essere generato da una prenotazione, quindi nullable
    inizio_noleggio DATETIME NOT NULL,
    fine_noleggio DATETIME,
    costo_finale DOUBLE,
    stato ENUM('IN_CORSO', 'IN_PAUSA', 'TERMINATO') NOT NULL DEFAULT 'IN_CORSO',
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (veicolo_id) REFERENCES veicolo(id) ON DELETE CASCADE,
    FOREIGN KEY (prenotazione_id) REFERENCES prenotazione(id) ON DELETE SET NULL
);
