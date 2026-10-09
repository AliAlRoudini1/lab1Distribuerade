SET NAMES utf8mb4;

DROP DATABASE IF EXISTS webbshop;
CREATE DATABASE webbshop CHARACTER SET utf8mb4 COLLATE utf8mb4_swedish_ci;
USE webbshop;

CREATE TABLE anvandare (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    anvandarnamn VARCHAR(50) NOT NULL UNIQUE,
    losenord     VARCHAR(50) NOT NULL,
    roll         ENUM('KUND', 'ADMIN', 'LAGER') NOT NULL DEFAULT 'KUND'
);

CREATE TABLE produkt (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    namn        VARCHAR(100) NOT NULL,
    beskrivning VARCHAR(255),
    pris        INT NOT NULL,
    lager_antal INT NOT NULL DEFAULT 0
);

CREATE TABLE ordrar (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    anvandare_id INT NOT NULL,
    datum        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (anvandare_id) REFERENCES anvandare(id)
);

CREATE TABLE orderrader (
    order_id   INT NOT NULL,
    produkt_id INT NOT NULL,
    antal      INT NOT NULL,
    pris       INT NOT NULL,
    PRIMARY KEY (order_id, produkt_id),
    FOREIGN KEY (order_id) REFERENCES ordrar(id),
    FOREIGN KEY (produkt_id) REFERENCES produkt(id)
);

INSERT INTO anvandare (anvandarnamn, losenord, roll) VALUES
    ('malek', 'hemligt', 'KUND'),
    ('kund',  'hemligt', 'KUND'),
    ('admin', 'hemligt', 'ADMIN'),
    ('lager', 'hemligt', 'LAGER');

INSERT INTO produkt (namn, beskrivning, pris, lager_antal) VALUES
    ('Bok',       'Kursbok',   99, 20),
    ('Dator',     'Bärbar',  1099,  5),
    ('Penna',     'Blyerts',    9, 50),
    ('Suddgummi', 'Vitt',       5,  0);
