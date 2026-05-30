USE smart_mobility;

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