USE smart_mobility;

-- Pulizia preventiva completa della flotta per reinserimento pulito
DELETE FROM veicolo WHERE codice_identificativo IN (
    'AUTO-001', 'AUTO-002', 'AUTO-003', 'AUTO-004', 'AUTO-005',
    'AUTO-006', 'AUTO-007', 'AUTO-008', 'AUTO-009', 'AUTO-010',
    'MONO-001', 'MONO-002', 'MONO-003', 'MONO-004', 'MONO-005',
    'MONO-006', 'MONO-007', 'MONO-008', 'MONO-009', 'MONO-010',
    'BIKE-001', 'BIKE-002', 'BIKE-003', 'BIKE-004', 'BIKE-005',
    'BIKE-006', 'BIKE-007', 'BIKE-008', 'BIKE-009', 'BIKE-010'
);

-- ============================================================
--  FLOTTA SMART MOBILITY BARI  (30 veicoli)
--  Quartieri coperti: Centro Storico, Murattiano, Madonnella,
--  Libertà, Japigia, Carrassi, San Pasquale, Poggiofranco,
--  Lungomare, Stazione, Università, Aeroporto, Torre a Mare
-- ============================================================

INSERT INTO veicolo
    (codice_identificativo, tipo_veicolo, latitudine, longitudine, livello_carica_residua, portata_massima, stato_operativo, targa, numero_posti, velocita_massima, pedalata_assistita)
VALUES

-- ==================== AUTOMOBILI (10) ====================

    -- AUTO-001 | Piazza del Ferrarese (Centro Storico) | DISPONIBILE
    ('AUTO-001', 'AUTOMOBILE', 41.1265, 16.8700, 92.0, 500.0, 'DISPONIBILE', 'BA001SM', 5, NULL, NULL),

    -- AUTO-002 | Lungomare Nazario Sauro | DISPONIBILE
    ('AUTO-002', 'AUTOMOBILE', 41.1171, 16.8719, 100.0, 500.0, 'DISPONIBILE', 'AB123CD', 5, NULL, NULL),

    -- AUTO-003 | Via Sparano (Centro Murattiano) | IN_USO
    ('AUTO-003', 'AUTOMOBILE', 41.1210, 16.8650, 45.5, 450.0, 'IN_USO', 'EF456GH', 4, NULL, NULL),

    -- AUTO-004 | Stazione Centrale FS | DISPONIBILE
    ('AUTO-004', 'AUTOMOBILE', 41.1128, 16.8720, 78.0, 500.0, 'DISPONIBILE', 'BA004SM', 5, NULL, NULL),

    -- AUTO-005 | Università degli Studi di Bari | DISPONIBILE
    ('AUTO-005', 'AUTOMOBILE', 41.1093, 16.8833, 55.0, 500.0, 'DISPONIBILE', 'BA005SM', 4, NULL, NULL),

    -- AUTO-006 | Piazza Aldo Moro (Centro Murattiano) | IN_USO
    ('AUTO-006', 'AUTOMOBILE', 41.1185, 16.8695, 33.0, 450.0, 'IN_USO', 'BA006SM', 4, NULL, NULL),

    -- AUTO-007 | Quartiere San Pasquale | DISPONIBILE
    ('AUTO-007', 'AUTOMOBILE', 41.1030, 16.8790, 88.0, 500.0, 'DISPONIBILE', 'BA007SM', 5, NULL, NULL),

    -- AUTO-008 | Fiera del Levante | IN_MANUTENZIONE
    ('AUTO-008', 'AUTOMOBILE', 41.1068, 16.8485, 10.0, 500.0, 'IN_MANUTENZIONE', 'BA008SM', 5, NULL, NULL),

    -- AUTO-009 | Quartiere Japigia | DISPONIBILE
    ('AUTO-009', 'AUTOMOBILE', 41.0862, 16.8890, 71.0, 450.0, 'DISPONIBILE', 'BA009SM', 4, NULL, NULL),

    -- AUTO-010 | Quartiere San Giorgio (Bari Est) | DISPONIBILE
    ('AUTO-010', 'AUTOMOBILE', 41.0955, 16.9020, 95.0, 500.0, 'DISPONIBILE', 'BA010SM', 5, NULL, NULL),


-- ==================== MONOPATTINI (10) ====================

    -- MONO-001 | Piazza Umberto I | DISPONIBILE
    ('MONO-001', 'MONOPATTINO', 41.1100, 16.8800, 85.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-002 | Lungomare di Bari (Pane e Pomodoro) | DISPONIBILE
    ('MONO-002', 'MONOPATTINO', 41.1028, 16.8700, 90.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-003 | Via Capruzzi (vicino Stazione) | IN_USO
    ('MONO-003', 'MONOPATTINO', 41.1135, 16.8738, 60.0, 100.0, 'IN_USO', NULL, NULL, 25, NULL),

    -- MONO-004 | Piazza Garibaldi | DISPONIBILE
    ('MONO-004', 'MONOPATTINO', 41.1170, 16.8672, 72.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-005 | Campus Universitario | DISPONIBILE
    ('MONO-005', 'MONOPATTINO', 41.1080, 16.8820, 45.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-006 | Quartiere Libertà | IN_USO
    ('MONO-006', 'MONOPATTINO', 41.1190, 16.8590, 38.0, 100.0, 'IN_USO', NULL, NULL, 25, NULL),

    -- MONO-007 | Piazza Moro | DISPONIBILE
    ('MONO-007', 'MONOPATTINO', 41.1145, 16.8693, 80.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-008 | Via Argiro (Centro) | BATTERIA_SCARICA
    ('MONO-008', 'MONOPATTINO', 41.1220, 16.8668, 4.0, 100.0, 'BATTERIA_SCARICA', NULL, NULL, 25, NULL),

    -- MONO-009 | Quartiere Poggiofranco | DISPONIBILE
    ('MONO-009', 'MONOPATTINO', 41.0982, 16.8655, 95.0, 100.0, 'DISPONIBILE', NULL, NULL, 25, NULL),

    -- MONO-010 | Carrassi (Viale della Repubblica) | IN_MANUTENZIONE
    ('MONO-010', 'MONOPATTINO', 41.1045, 16.8780, 0.0, 100.0, 'IN_MANUTENZIONE', NULL, NULL, 25, NULL),


-- ==================== BICICLETTE (10) ====================

    -- BIKE-001 | Castello Svevo (Bari Vecchia) | IN_MANUTENZIONE (E-Bike)
    ('BIKE-001', 'BICICLETTA', 41.1253, 16.8702, 0.0, 120.0, 'IN_MANUTENZIONE', NULL, NULL, NULL, TRUE),

    -- BIKE-002 | Lungomare Imperatore Augusto | DISPONIBILE (Classica)
    ('BIKE-002', 'BICICLETTA', 41.1120, 16.8680, 60.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, FALSE),

    -- BIKE-003 | Parco 2 Giugno | DISPONIBILE (E-Bike)
    ('BIKE-003', 'BICICLETTA', 41.1010, 16.8730, 88.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, TRUE),

    -- BIKE-004 | Piazza Cesare Battisti | DISPONIBILE (Classica)
    ('BIKE-004', 'BICICLETTA', 41.1155, 16.8800, 50.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, FALSE),

    -- BIKE-005 | Quartiere Libertà Nord (Via Giovinazzo) | IN_USO (E-Bike)
    ('BIKE-005', 'BICICLETTA', 41.1268, 16.8548, 70.0, 120.0, 'IN_USO', NULL, NULL, NULL, TRUE),

    -- BIKE-006 | Porto di Bari (Banchina San Cataldo) | DISPONIBILE (Classica)
    ('BIKE-006', 'BICICLETTA', 41.1245, 16.8690, 40.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, FALSE),

    -- BIKE-007 | Via Amendola (Carrassi) | DISPONIBILE (E-Bike)
    ('BIKE-007', 'BICICLETTA', 41.1058, 16.8832, 95.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, TRUE),

    -- BIKE-008 | Policlinico di Bari | IN_USO (Classica)
    ('BIKE-008', 'BICICLETTA', 41.1070, 16.8900, 55.0, 120.0, 'IN_USO', NULL, NULL, NULL, FALSE),

    -- BIKE-009 | Aeroporto Palese (Parcheggio Aerostazione) | DISPONIBILE (E-Bike)
    ('BIKE-009', 'BICICLETTA', 41.1405, 16.7780, 80.0, 120.0, 'DISPONIBILE', NULL, NULL, NULL, TRUE),

    -- BIKE-010 | Quartiere Japigia Sud | BATTERIA_SCARICA (E-Bike)
    ('BIKE-010', 'BICICLETTA', 41.0780, 16.8910, 3.0, 120.0, 'BATTERIA_SCARICA', NULL, NULL, NULL, TRUE);
