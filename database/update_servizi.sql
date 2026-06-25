CREATE TABLE sessione_assistenza (
    id_sessione VARCHAR(50) PRIMARY KEY,
    account_id INT NOT NULL, -- Utente che richiede assistenza
    operatore_id INT, -- Operatore che gestisce la sessione
    categoria_problema VARCHAR(100) NOT NULL,
    dettagli_preliminari TEXT NOT NULL,
    istante_avvio DATETIME NOT NULL,
    stato ENUM('IN_ATTESA', 'IN_CORSO', 'TERMINATA') NOT NULL DEFAULT 'IN_ATTESA',
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (operatore_id) REFERENCES account(id) ON DELETE SET NULL
);
