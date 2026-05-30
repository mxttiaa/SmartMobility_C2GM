USE smart_mobility;

CREATE TABLE IF NOT EXISTS Mezzo (
    idMezzo INT AUTO_INCREMENT PRIMARY KEY,
    tipologia VARCHAR(50) NOT NULL,
    portataMassima DOUBLE NOT NULL,
    livelloBatteria DOUBLE NOT NULL
);

INSERT INTO Mezzo (tipologia, portataMassima, livelloBatteria) VALUES 
('Monopattino', 100.0, 85.5),
('Bici Elettrica', 120.0, 40.0),
('Scooter Elettrico', 150.0, 15.0);
