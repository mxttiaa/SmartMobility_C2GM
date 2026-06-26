USE smart_mobility;

-- Inserimento promozioni per mario.rossi@email.it (se esiste)
INSERT INTO promozione (account_id, codice_alfanumerico, valore_sconto, data_scadenza)
SELECT id, 'SCONTO5', 5.0, DATE_ADD(NOW(), INTERVAL 30 DAY) 
FROM account 
WHERE email = 'mario.rossi@email.it';

INSERT INTO promozione (account_id, codice_alfanumerico, valore_sconto, data_scadenza)
SELECT id, 'BENVENUTO', 2.0, DATE_ADD(NOW(), INTERVAL 7 DAY) 
FROM account 
WHERE email = 'mario.rossi@email.it';
