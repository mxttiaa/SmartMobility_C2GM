USE smartmobility;

-- Pulizia preventiva nel caso in cui i record esistano già per evitare errori di chiave duplicata
DELETE FROM veicolo WHERE codice_identificativo IN ('AUTO-002', 'AUTO-003', 'MONO-001', 'BIKE-001', 'BIKE-002');

-- Inserimento di 5 veicoli di test (Single Table Strategy)
INSERT INTO veicolo 
    (codice_identificativo, tipo_veicolo, latitudine, longitudine, livello_carica_residua, portata_massima, stato_operativo, targa, numero_posti, velocita_massima, pedalata_assistita)
VALUES 
    -- 1. Automobile DISPONIBILE
    ('AUTO-002', 'AUTOMOBILE', 45.4642, 9.1900, 100.0, 500.0, 'DISPONIBILE', 'AB123CD', 5, NULL, NULL),

    -- 2. Automobile IN_USO
    ('AUTO-003', 'AUTOMOBILE', 45.4700, 9.2000, 45.5, 450.0, 'IN_USO', 'EF456GH', 4, NULL, NULL),

    -- 3. Monopattino DISPONIBILE
    ('MONO-001', 'MONOPATTINO', 45.4800, 9.1800, 85.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- 4. Bicicletta IN_MANUTENZIONE (E-Bike)
    ('BIKE-001', 'BICICLETTA', 45.4600, 9.1700, 0.0, 120.0, 'IN_MANUTENZIONE', NULL, NULL, NULL, TRUE),

    -- 5. Bicicletta DISPONIBILE (Classica)
    ('BIKE-002', 'BICICLETTA', 45.4650, 9.1850, 60.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, FALSE);
