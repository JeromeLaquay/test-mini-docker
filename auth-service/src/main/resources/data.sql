-- Suppression des données existantes
-- TRUNCATE TABLE user_roles CASCADE;
-- TRUNCATE TABLE roles CASCADE;
-- TRUNCATE TABLE users CASCADE;

-- Insertion des rôles (à exécuter une seule fois)
INSERT INTO roles(name) VALUES('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO roles(name) VALUES('ROLE_MODERATOR') ON CONFLICT DO NOTHING;
INSERT INTO roles(name) VALUES('ROLE_ADMIN') ON CONFLICT DO NOTHING;

-- Création d'un utilisateur admin (mot de passe : 123456)
-- Mot de passe encodé avec BCrypt ($2a$10$PYmzM/g.ydpQcH1CBkZT1u0mSKUYx5FPcJaYBMiL1BSSNs4KnTGKu)
INSERT INTO users(username, email, password) 
VALUES('admin', 'admin@admin.com', '$2a$10$PYmzM/g.ydpQcH1CBkZT1u0mSKUYx5FPcJaYBMiL1BSSNs4KnTGKu')
ON CONFLICT (username) DO NOTHING;



-- Assigner le rôle ADMIN à l'utilisateur admin
INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- Pour l'utilisateur jerome, si vous l'avez déjà créé
-- D'abord vérifier s'il existe
INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'jerome' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;