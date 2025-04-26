package com.jeroka.subscription.dto;

import com.jeroka.subscription.model.SubscriptionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO pour l'entité Subscription.
 */
public class SubscriptionDto {

    private Long id;
    
    @NotNull(message = "L'ID utilisateur est obligatoire")
    private String userId;
    
    @NotNull(message = "Le type d'abonnement est obligatoire")
    private SubscriptionType type;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private boolean autoRenew;
    private Double price;
    
    // Constructeurs
    public SubscriptionDto() {
    }
    
    public SubscriptionDto(Long id, String userId, SubscriptionType type, LocalDateTime startDate,
                          LocalDateTime endDate, boolean isActive, boolean autoRenew, Double price) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.autoRenew = autoRenew;
        this.price = price;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public SubscriptionType getType() {
        return type;
    }
    
    public void setType(SubscriptionType type) {
        this.type = type;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public boolean isAutoRenew() {
        return autoRenew;
    }
    
    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
} 