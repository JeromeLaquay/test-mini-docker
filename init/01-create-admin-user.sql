-- Insérer le rôle "ADMIN"
INSERT INTO roles (name, description) 
VALUES ('ADMIN', 'Administrateur');

-- Insérer l'utilisateur admin
INSERT INTO users (username, email, password, enabled)
VALUES ('admin', 'admin@example.com', '$2a$10$hWFhsSsxSITtdCqBs9Vu/OLki.8Zq3jROm/oHydJNvGdg9P1HHKsq', true);

-- Lier l'utilisateur admin au rôle "ADMIN"
INSERT INTO user_roles (user_id, role_id)
VALUES (
  (SELECT id FROM users WHERE username = 'admin'),
  (SELECT id FROM roles WHERE name = 'ADMIN')
);

-- Insérer l'utilisateur jerome
INSERT INTO users (username, email, password, enabled)
VALUES (
  'jerome',
  'jerome.' || SUBSTRING(MD5(RANDOM()::TEXT), 1, 8) || '@example.com',
  '$2a$10$8eXMoTg2sWOBpW.dPwdVZOKOhvXQCTH/KpXIBTwSesNSLXVU0JRfS',
  true
);

-- Lier l'utilisateur jerome au rôle "ADMIN"
INSERT INTO user_roles (user_id, role_id) 
VALUES (
  (SELECT id FROM users WHERE username = 'jerome'),
  (SELECT id FROM roles WHERE name = 'ADMIN')
); 