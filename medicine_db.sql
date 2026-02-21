CREATE DATABASE medicine_db;
USE medicine_db;

CREATE TABLE medicines (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    morningDose VARCHAR(50),
    morningTime VARCHAR(20),
    morningFood VARCHAR(50),
    noonDose VARCHAR(50),
    noonTime VARCHAR(20),
    noonFood VARCHAR(50),
    nightDose VARCHAR(50),
    nightTime VARCHAR(20),
    nightFood VARCHAR(50),
    duration INT NOT NULL,
    startDate DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
USE medicine_db;