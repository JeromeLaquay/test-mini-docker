-- Création de la table des rôles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

-- Création de la table des utilisateurs
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    telephone VARCHAR(20),
    adresse TEXT,
    code_postal VARCHAR(10),
    ville VARCHAR(100),
    pays VARCHAR(100),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table de relation entre utilisateurs et rôles
CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Table plans
CREATE TABLE plans (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prix_mensuel DECIMAL(10,2) NOT NULL,
    prix_annuel DECIMAL(10,2),
    periode_essai_jours INTEGER DEFAULT 0,
    actif BOOLEAN NOT NULL DEFAULT TRUE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Table méthodes de paiement
CREATE TABLE methodes_paiement (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('CARTE', 'PAYPAL')),
    token VARCHAR(255) NOT NULL,
    identifiant_externe VARCHAR(255),
    derniers_chiffres VARCHAR(4),
    date_expiration DATE,
    marque VARCHAR(50),
    nom_titulaire VARCHAR(255),
    par_defaut BOOLEAN NOT NULL DEFAULT FALSE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Trigger pour garantir qu'un client n'a qu'une seule méthode de paiement par défaut
CREATE OR REPLACE FUNCTION verifier_methode_paiement_par_defaut()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.par_defaut = TRUE THEN
        UPDATE methodes_paiement 
        SET par_defaut = FALSE 
        WHERE user_id = NEW.user_id 
        AND id != NEW.id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trigger_methode_paiement_par_defaut
BEFORE INSERT OR UPDATE ON methodes_paiement
FOR EACH ROW
EXECUTE FUNCTION verifier_methode_paiement_par_defaut();
-- Table abonnements
CREATE TABLE abonnements (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    plan_id INTEGER NOT NULL REFERENCES plans(id),
    methode_paiement_id INTEGER REFERENCES methodes_paiement(id),
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('ACTIF', 'ESSAI', 'SUSPENDU', 'ANNULE', 'EXPIRE')),
    periode VARCHAR(10) NOT NULL CHECK (periode IN ('MENSUEL', 'ANNUEL')),
    date_debut TIMESTAMP NOT NULL,
    date_fin_essai TIMESTAMP,
    date_prochaine_facturation TIMESTAMP,
    date_fin TIMESTAMP,
    montant DECIMAL(10,2) NOT NULL,
    auto_renouvellement BOOLEAN NOT NULL DEFAULT TRUE,
    raison_annulation TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Table transactions
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    abonnement_id INTEGER REFERENCES abonnements(id),
    user_id INTEGER NOT NULL REFERENCES users(id),
    methode_paiement_id INTEGER REFERENCES methodes_paiement(id),
    reference VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('PAIEMENT', 'REMBOURSEMENT', 'ECHEC', 'TEST')),
    montant DECIMAL(10,2) NOT NULL,
    devise VARCHAR(3) NOT NULL DEFAULT 'EUR',
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('EN_ATTENTE', 'REUSSIE', 'ECHOUEE', 'REMBOURSEE', 'ANNULEE')),
    prestataire VARCHAR(50) NOT NULL,
    identifiant_externe VARCHAR(255),
    description TEXT,
    metadata JSONB,
    code_erreur VARCHAR(100),
    message_erreur TEXT,
    date_transaction TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Table factures
CREATE TABLE factures (
    id SERIAL PRIMARY KEY,
    transaction_id INTEGER NOT NULL REFERENCES transactions(id),
    user_id INTEGER NOT NULL REFERENCES users(id),
    numero VARCHAR(50) NOT NULL UNIQUE,
    date_emission DATE NOT NULL,
    date_paiement DATE,
    montant_ht DECIMAL(10,2) NOT NULL,
    taux_tva DECIMAL(5,2) NOT NULL,
    montant_tva DECIMAL(10,2) NOT NULL,
    montant_ttc DECIMAL(10,2) NOT NULL,
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('EMISE', 'EN_ATTENTE', 'PAYEE', 'ANNULEE', 'REMBOURSEE')),
    url_pdf VARCHAR(255),
    notes TEXT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Table historique des activités (pour l'audit)
CREATE TABLE historique_activites (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    type_entite VARCHAR(50) NOT NULL,
    id_entite INTEGER NOT NULL,
    action VARCHAR(50) NOT NULL,
    details JSONB,
    adresse_ip VARCHAR(45),
    user_agent TEXT,
    date_action TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Index pour améliorer les performances
CREATE INDEX idx_abonnements_user_id ON abonnements(user_id);
CREATE INDEX idx_abonnements_statut ON abonnements(statut);
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(date_transaction);
CREATE INDEX idx_transactions_statut ON transactions(statut);
CREATE INDEX idx_factures_user_id ON factures(user_id);
CREATE INDEX idx_methodes_paiement_user_id ON methodes_paiement(user_id);
-- Fonction pour mettre à jour automatiquement le timestamp de mise à jour
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.date_mise_a_jour = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- Triggers pour la mise à jour automatique des timestamps
CREATE TRIGGER update_users_modtime
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_plans_modtime
BEFORE UPDATE ON plans
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_methodes_paiement_modtime
BEFORE UPDATE ON methodes_paiement
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();
CREATE TRIGGER update_abonnements_modtime
BEFORE UPDATE ON abonnements
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

