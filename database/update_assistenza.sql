CREATE TABLE IF NOT EXISTS RichiestaAssistenza (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT NOT NULL,
    descrizioneProblema TEXT NOT NULL,
    stato VARCHAR(20) DEFAULT 'aperta',
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE
);
