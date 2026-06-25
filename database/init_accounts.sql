-- Smart Mobility: Script per aggiungere autenticazione reale
-- Aggiunge password_hash e ruolo alla tabella account

USE smart_mobility;

-- 1. Aggiungere le colonne di autenticazione
ALTER TABLE account ADD COLUMN password_hash VARCHAR(255) NOT NULL DEFAULT '' AFTER email;
ALTER TABLE account ADD COLUMN ruolo ENUM('CLIENTE','OPERATORE','AMMINISTRATORE') NOT NULL DEFAULT 'CLIENTE' AFTER password_hash;

-- 2. Popolare utenti di test con ruoli reali
-- Password di test: "password123" → SHA-256 hash
-- Hash precalcolato di "password123": ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f

INSERT INTO account (nome, cognome, email, password_hash, ruolo, saldo_crediti_bonus, stato) VALUES
('Mario', 'Rossi', 'mario.rossi@email.it', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'CLIENTE', 0.0, 'ATTIVO'),
('Laura', 'Bianchi', 'laura.bianchi@email.it', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'CLIENTE', 5.0, 'ATTIVO'),
('Giuseppe', 'Verdi', 'giuseppe.verdi@email.it', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'OPERATORE', 0.0, 'ATTIVO'),
('Anna', 'Neri', 'anna.neri@email.it', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'OPERATORE', 0.0, 'ATTIVO'),
('Admin', 'Sistema', 'admin@smartmobility.it', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'AMMINISTRATORE', 0.0, 'ATTIVO')
ON DUPLICATE KEY UPDATE password_hash = VALUES(password_hash), ruolo = VALUES(ruolo), stato = VALUES(stato);
