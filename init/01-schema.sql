-- Suppression des tables si elles existent
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Table des utilisateurs
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);

-- Table des rôles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT uk_roles_name UNIQUE (name)
);

-- Table de liaison users_roles (relation many-to-many)
CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    granted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- Fonction pour mettre à jour automatiquement updated_at
-- CREATE OR REPLACE FUNCTION update_updated_at_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = CURRENT_TIMESTAMP;
-- RETURN NEW;
-- END;
-- $$ language 'plpgsql';

-- -- Trigger pour mettre à jour automatiquement updated_at
-- CREATE TRIGGER update_users_updated_at
--     BEFORE UPDATE ON users
--     FOR EACH ROW
--     EXECUTE FUNCTION update_updated_at_column(); 