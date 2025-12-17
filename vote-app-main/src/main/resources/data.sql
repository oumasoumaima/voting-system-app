-- Suppression des données existantes (optionnel)
DELETE FROM voters;

-- Insertion de quelques électeurs de test
INSERT INTO voters (cin, first_name, last_name, email, date_of_birth, address, has_voted, is_active, registration_date)
VALUES ('AB123456', 'Aicha', 'Bennani', 'aicha.bennani@email.com', '1990-05-15', '123 Rue Hassan II, Casablanca', false, true, CURRENT_TIMESTAMP);

INSERT INTO voters (cin, first_name, last_name, email, date_of_birth, address, has_voted, is_active, registration_date)
VALUES ('CD789012', 'Fatima', 'Alaoui', 'fatima.alaoui@email.com', '1985-08-22', '456 Avenue Mohammed V, Rabat', false, true, CURRENT_TIMESTAMP);

INSERT INTO voters (cin, first_name, last_name, email, date_of_birth, address, has_voted, is_active, registration_date)
VALUES ('EF345678', 'Kawtar', 'El Idrissi', 'kawtar.idrissi@email.com', '1992-11-30', '789 Boulevard Zerktouni, Marrakech', false, true, CURRENT_TIMESTAMP);

INSERT INTO voters (cin, first_name, last_name, email, date_of_birth, address, has_voted, is_active, registration_date)
VALUES ('GH901234', 'Khadija', 'Fassi', 'khadija.fassi@email.com', '1988-03-10', '321 Rue de la Liberté, Fès', false, true, CURRENT_TIMESTAMP);

INSERT INTO voters (cin, first_name, last_name, email, date_of_birth, address, has_voted, is_active, registration_date)
VALUES ('IJ567890', 'Omar', 'Tazi', 'omar.tazi@email.com', '1995-07-18', '654 Avenue Hassan II, Tanger', false, true, CURRENT_TIMESTAMP);