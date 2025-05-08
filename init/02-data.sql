-- Arrêt forcé du script si une erreur survient
\set ON_ERROR_STOP true

-- Insertion des rôles
INSERT INTO roles (name) VALUES 
('ROLE_ADMIN'),
('ROLE_USER');

-- Insertion des utilisateurs
INSERT INTO users (username, email, password, date_creation, date_mise_a_jour) VALUES 
('admin1', 'admin1@example.com', '$2a$10$X7J3Y5Z8A9B0C1D2E3F4G5H6I7J8K9L0M1N2O3P4Q5R6S7T8U9V0W1X2Y3Z4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jerome', 'jerome@example.com', '$2a$10$/vq9kjhmF5BaNcS93U776.8BEzAyPVIp832zb3YMkkVcgJDwdWLDS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Attribution des rôles aux utilisateurs
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- admin1 est admin
(2, 2); -- user1 est user

-- Insertion des plans
INSERT INTO plans (nom, description, prix_mensuel, prix_annuel, periode_essai_jours, actif) VALUES 
('Premium', 'Plan premium avec toutes les fonctionnalités', 29.99, 299.99, 14, true),
('Standard', 'Plan standard avec fonctionnalités de base', 19.99, 199.99, 7, true);

-- Insertion des méthodes de paiement
INSERT INTO methodes_paiement (user_id, type, token, derniers_chiffres, date_expiration, marque, nom_titulaire, par_defaut) VALUES 
(1, 'CARTE', 'tok_visa_1234', '4242', '2025-12-31', 'Visa', 'Jean Dupont', true),
(2, 'CARTE', 'tok_mc_5678', '5555', '2025-12-31', 'MasterCard', 'Sophie Martin', true);

-- Insertion des abonnements
INSERT INTO abonnements (user_id, plan_id, methode_paiement_id, statut, periode, date_debut, date_fin_essai, date_prochaine_facturation, montant, auto_renouvellement) VALUES 
(1, 1, 1, 'ACTIF', 'MENSUEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '14 days', CURRENT_TIMESTAMP + INTERVAL '1 month', 29.99, true),
(2, 2, 2, 'ESSAI', 'MENSUEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '7 days', CURRENT_TIMESTAMP + INTERVAL '7 days', 19.99, true);

-- Insertion des transactions
INSERT INTO transactions (abonnement_id, user_id, methode_paiement_id, reference, type, montant, statut, prestataire, description) VALUES 
(1, 1, 1, 'TRX001', 'PAIEMENT', 29.99, 'REUSSIE', 'Stripe', 'Paiement mensuel Premium'),
(2, 2, 2, 'TRX002', 'TEST', 0.00, 'REUSSIE', 'Stripe', 'Paiement test Standard');

-- Insertion des factures
INSERT INTO factures (transaction_id, user_id, numero, date_emission, date_paiement, montant_ht, taux_tva, montant_tva, montant_ttc, statut) VALUES 
(1, 1, 'FACT001', CURRENT_DATE, CURRENT_DATE, 24.99, 20.00, 5.00, 29.99, 'PAYEE'),
(2, 2, 'FACT002', CURRENT_DATE, CURRENT_DATE, 0.00, 20.00, 0.00, 0.00, 'EMISE');

-- Insertion des activités
INSERT INTO historique_activites (user_id, type_entite, id_entite, action, details, adresse_ip, user_agent) VALUES 
(1, 'ABONNEMENT', 1, 'CREATION', '{"plan": "Premium", "periode": "MENSUEL"}', '192.168.1.1', 'Mozilla/5.0'),
(2, 'ABONNEMENT', 2, 'CREATION', '{"plan": "Standard", "periode": "MENSUEL"}', '192.168.1.2', 'Mozilla/5.0'); 