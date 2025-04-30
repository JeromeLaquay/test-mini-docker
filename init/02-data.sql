-- Arrêt forcé du script si une erreur survient
\set ON_ERROR_STOP true

-- Insertion des rôles
INSERT INTO roles (name) VALUES 
('ROLE_USER'),
('ROLE_ADMIN');

-- Création d'un utilisateur administrateur
-- Mot de passe encodé en BCrypt: "password"
INSERT INTO users (username, email, password) VALUES 
('admin', 'admin@example.com', '$2a$10$yfxJIew5GJFxeKJKt4aJouekXxAIXX2BXBb8M6U9E2YgM/V0wfKH2');

-- Attribution du rôle administrateur
INSERT INTO user_roles (user_id, role_id) 
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

-- Insertion des albums
INSERT INTO albums (id, name, user_id, background_image, title_color, song_color, 
                    title_background_opacity, songs_background_opacity, overlay_opacity, 
                    overlay_color, background_image_opacity, title_alignment, songs_alignment)
VALUES 
('5a1e0332-548c-4a50-a8d5-dc11fb90ba0e', 'Album Démo 1', '1', 
 'https://images.unsplash.com/photo-1526047932273-341f2a7631f9', 
 '#ffffff', '#ffffff', 50, 50, 100, '#000000', 50, 'center', 'center'),
('b89c6f80-3c6d-4842-9d5d-ad0698978a83', 'Album Démo 2', '1', 
 'https://images.unsplash.com/photo-1493612276216-ee3925520721', 
 '#ffffff', '#ffffff', 50, 50, 50, '#000000', 100, 'center', 'center');

-- Insertion des chansons pour les albums
INSERT INTO album_songs (album_id, song_name) VALUES 
('5a1e0332-548c-4a50-a8d5-dc11fb90ba0e', 'Chanson 1'),
('5a1e0332-548c-4a50-a8d5-dc11fb90ba0e', 'Chanson 2'),
('5a1e0332-548c-4a50-a8d5-dc11fb90ba0e', 'Chanson 3'),
('b89c6f80-3c6d-4842-9d5d-ad0698978a83', 'Symphonie n°5'),
('b89c6f80-3c6d-4842-9d5d-ad0698978a83', 'Clair de Lune'),
('b89c6f80-3c6d-4842-9d5d-ad0698978a83', 'Le Printemps');

-- Insertion des plans d'abonnement
INSERT INTO subscription_plans (name, description, type, price, duration_months, features, is_active)
VALUES 
('Gratuit', 'Plan de base avec des fonctionnalités limitées', 'FREE', 0.00, NULL, 'Création d''album limité,Stockage de base', TRUE),
('Premium Mensuel', 'Accès à toutes les fonctionnalités avec paiement mensuel', 'MONTHLY', 9.99, 1, 'Albums illimités,Stockage premium,Support prioritaire', TRUE),
('Premium Annuel', 'Accès à toutes les fonctionnalités avec paiement annuel', 'YEARLY', 99.99, 12, 'Albums illimités,Stockage premium,Support dédié,Réduction de 17%', TRUE),
('À vie', 'Accès permanent à toutes les fonctionnalités', 'ONE_TIME', 299.99, NULL, 'Albums illimités,Stockage premium,Support VIP,Accès à vie', TRUE);

-- Insertion des abonnements utilisateurs
INSERT INTO subscriptions (user_id, type, start_date, end_date, is_active, auto_renew, price)
VALUES
-- Utilisateur 1 avec abonnement gratuit
(1, 'FREE', CURRENT_TIMESTAMP - INTERVAL '60 days', NULL, TRUE, FALSE, 0.00),
-- Utilisateur 1 avec abonnement mensuel
(1, 'MONTHLY', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP + INTERVAL '15 days', TRUE, TRUE, 9.99),
-- Utilisateur 1 avec abonnement annuel
(1, 'YEARLY', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP + INTERVAL '11 months', TRUE, TRUE, 99.99),
-- Utilisateur 1 avec abonnement à vie
(1, 'ONE_TIME', CURRENT_TIMESTAMP - INTERVAL '90 days', NULL, TRUE, FALSE, 299.99),
-- Utilisateur 1 avec abonnement expiré
(1, 'MONTHLY', CURRENT_TIMESTAMP - INTERVAL '60 days', CURRENT_TIMESTAMP - INTERVAL '30 days', FALSE, FALSE, 9.99);

-- Insertion des factures de test
INSERT INTO invoices (
    id,
    invoice_number,
    created_at,
    order_id,
    order_date,
    customer_name,
    customer_email,
    customer_address,
    subtotal,
    tax_rate,
    tax_amount,
    total
) VALUES 
-- Facture 1 : Abonnement mensuel
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d479',
    'INV-2024-001',
    CURRENT_TIMESTAMP - INTERVAL '30 days',
    'ORDER-2024-001',
    CURRENT_TIMESTAMP - INTERVAL '30 days',
    'John Doe',
    'john.doe@example.com',
    '123 Rue de la Paix, 75000 Paris',
    9.99,  -- Prix de l'abonnement mensuel
    20.00, -- TVA 20%
    2.00,  -- Montant TVA
    11.99  -- Total
),
-- Facture 2 : Abonnement annuel
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d480',
    'INV-2024-002',
    CURRENT_TIMESTAMP - INTERVAL '15 days',
    'ORDER-2024-002',
    CURRENT_TIMESTAMP - INTERVAL '15 days',
    'Jane Smith',
    'jane.smith@example.com',
    '456 Avenue des Champs-Élysées, 75008 Paris',
    99.99, -- Prix de l'abonnement annuel
    20.00, -- TVA 20%
    20.00, -- Montant TVA
    119.99 -- Total
),
-- Facture 3 : Abonnement à vie
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d481',
    'INV-2024-003',
    CURRENT_TIMESTAMP - INTERVAL '7 days',
    'ORDER-2024-003',
    CURRENT_TIMESTAMP - INTERVAL '7 days',
    'Alice Johnson',
    'alice.johnson@example.com',
    '789 Boulevard Haussmann, 75009 Paris',
    299.99, -- Prix de l'abonnement à vie
    20.00,  -- TVA 20%
    60.00,  -- Montant TVA
    359.99  -- Total
);

-- Insertion des items de facture
INSERT INTO invoice_items (
    invoice_id,
    description,
    unit_price,
    quantity,
    total
) VALUES 
-- Items pour la facture 1
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d479',
    'Abonnement Premium Mensuel',
    9.99,
    1,
    9.99
),
-- Items pour la facture 2
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d480',
    'Abonnement Premium Annuel',
    99.99,
    1,
    99.99
),
-- Items pour la facture 3
(
    'f47ac10b-58cc-4372-a567-0e02b2c3d481',
    'Abonnement Premium à Vie',
    299.99,
    1,
    299.99
); 