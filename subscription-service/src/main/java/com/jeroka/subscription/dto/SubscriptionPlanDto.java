package com.jeroka.subscription.dto;

import com.jeroka.subscription.model.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO pour l'entité SubscriptionPlan.
 */
public class SubscriptionPlanDto {

    private Long id;
    
    @NotBlank(message = "Le nom du plan est obligatoire")
    private String name;
    
    private String description;
    
    @NotNull(message = "Le type d'abonnement est obligatoire")
    private SubscriptionType type;
    
    @PositiveOrZero(message = "Le prix doit être positif ou zéro")
    private Double price;
    
    private Integer durationMonths;
    private String features;
    private boolean isActive;
    
    // Constructeurs
    public SubscriptionPlanDto() {
    }
    
    public SubscriptionPlanDto(Long id, String name, String description, SubscriptionType type,
                              Double price, Integer durationMonths, String features, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.durationMonths = durationMonths;
        this.features = features;
        this.isActive = isActive;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public SubscriptionType getType() {
        return type;
    }
    
    public void setType(SubscriptionType type) {
        this.type = type;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getDurationMonths() {
        return durationMonths;
    }
    
    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }
    
    public String getFeatures() {
        return features;
    }
    
    public void setFeatures(String features) {
        this.features = features;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
} 