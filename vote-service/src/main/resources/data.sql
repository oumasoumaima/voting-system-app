-- Candidats (utiliser MERGE pour éviter les doublons)
MERGE INTO candidates (id, name, party, program)
    KEY(name)
    VALUES
    (1, 'Mohammed Alami', 'Parti Démocratique', 'Programme axé sur l''éducation et la santé'),
    (2, 'Fatima Zahra Bennani', 'Parti Progressiste', 'Programme de développement économique'),
    (3, 'Youssef El Fassi', 'Parti Social', 'Programme de justice sociale'),
    (4, 'Khadija Idrissi', 'Parti Écologiste', 'Programme environnemental');

-- Proposition
MERGE INTO proposals (id, title, description, start_date, end_date, active)
    KEY(title)
    VALUES
    (1, 'Élection Présidentielle 2025', 'Élection pour le nouveau président',
    CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP), true);