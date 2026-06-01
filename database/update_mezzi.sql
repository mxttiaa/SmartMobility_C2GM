USE smart_mobility;

CREATE TABLE IF NOT EXISTS Mezzo (
    idMezzo INT AUTO_INCREMENT PRIMARY KEY,
    tipologia VARCHAR(50) NOT NULL,
    portataMassima DOUBLE NOT NULL,
    livelloBatteria DOUBLE NOT NULL,
    latitudine DOUBLE NOT NULL,
    longitudine DOUBLE NOT NULL
);

INSERT INTO Mezzo (tipologia, portataMassima, livelloBatteria, latitudine, longitudine) VALUES 
('Monopattino', 100.0, 85.5, 41.1180, 16.8720),
('Bici Elettrica', 120.0, 40.0, 41.1150, 16.8710),
('Scooter Elettrico', 150.0, 15.0, 41.1200, 16.8750);
