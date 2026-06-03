CREATE TABLE IF NOT EXISTS tariffa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_mezzo VARCHAR(50) NOT NULL UNIQUE,
    costo_base DECIMAL(10,2) NOT NULL,
    costo_minuto DECIMAL(10,2) NOT NULL,
    costo_km DECIMAL(10,2) DEFAULT 0.00
);

INSERT INTO tariffa (tipo_mezzo, costo_base, costo_minuto, costo_km) VALUES
('bici', 1.00, 0.20, 0.00),
('monopattino', 1.00, 0.15, 0.00),
('auto', 1.00, 0.25, 0.00)
ON DUPLICATE KEY UPDATE 
costo_base = VALUES(costo_base), 
costo_minuto = VALUES(costo_minuto), 
costo_km = VALUES(costo_km);
