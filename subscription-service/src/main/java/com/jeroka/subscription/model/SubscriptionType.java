package com.jeroka.subscription.model;

/**
 * Enumération des différents types d'abonnements disponibles.
 */
public enum SubscriptionType {
    FREE("Gratuit"),
    MONTHLY("Mensuel"),
    YEARLY("Annuel"),
    ONE_TIME("À vie");
    
    private final String displayName;
    
    SubscriptionType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 