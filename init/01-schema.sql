-- Arrêt forcé du script si une erreur survient
\set ON_ERROR_STOP true

-- Suppression des tables existantes (si nécessaires)
DROP TABLE IF EXISTS album_songs CASCADE;
DROP TABLE IF EXISTS albums CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS subscription_plans CASCADE;
DROP TABLE IF EXISTS subscriptions CASCADE;

-- Création de la table des rôles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

-- Création de la table des utilisateurs
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table de relation entre utilisateurs et rôles
CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Création de la table des albums (adaptée selon Album.java)
CREATE TABLE albums (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    background_image TEXT,
    title_color VARCHAR(7),
    song_color VARCHAR(7),
    title_background_opacity INTEGER,
    songs_background_opacity INTEGER,
    background_image_opacity INTEGER,
    overlay_opacity INTEGER,
    overlay_color VARCHAR(7),
    title_alignment VARCHAR(10),
    songs_alignment VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table des chansons d'album (adaptée selon Album.java)
CREATE TABLE album_songs (
    album_id UUID NOT NULL,
    song_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE
);

-- Création de la table des plans d'abonnement
CREATE TABLE subscription_plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    duration_months INTEGER,
    features TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_subscription_type CHECK (type IN ('FREE', 'MONTHLY', 'YEARLY', 'ONE_TIME')),
    CONSTRAINT check_price CHECK (price >= 0)
);

-- Création de la table des abonnements utilisateurs
CREATE TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    type VARCHAR(20) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    auto_renew BOOLEAN DEFAULT FALSE,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_subscription_type CHECK (type IN ('FREE', 'MONTHLY', 'YEARLY', 'ONE_TIME')),
    CONSTRAINT check_dates CHECK (end_date IS NULL OR end_date > start_date),
    CONSTRAINT check_price CHECK (price >= 0),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
CREATE INDEX idx_albums_user_id ON albums(user_id);
CREATE INDEX idx_album_songs_album_id ON album_songs(album_id);
CREATE INDEX idx_subscriptions_user_id ON subscriptions(user_id);
CREATE INDEX idx_subscriptions_is_active ON subscriptions(is_active);

-- Fonction pour mettre à jour automatiquement updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

-- Triggers pour mettre à jour automatiquement updated_at
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_albums_updated_at
    BEFORE UPDATE ON albums
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Triggers pour mettre à jour automatiquement updated_at sur les tables d'abonnement
CREATE TRIGGER update_subscription_plans_updated_at
    BEFORE UPDATE ON subscription_plans
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_subscriptions_updated_at
    BEFORE UPDATE ON subscriptions
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

