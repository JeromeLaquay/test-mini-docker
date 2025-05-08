package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.TypeTransaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class TransactionRequestDTO {
    @NotNull
    private Long userId;

    private Long abonnementId;

    private Long methodePaiementId;

    @NotNull
    private TypeTransaction type;

    @NotNull
    @PositiveOrZero
    private BigDecimal montant;

    @NotBlank
    @Size(min = 3, max = 3)
    private String devise = "EUR";

    @NotBlank
    private String prestataire;

    private String description;

    private String metadata;

    // Constructeurs
    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(Long userId, TypeTransaction type, BigDecimal montant, String prestataire) {
        this.userId = userId;
        this.type = type;
        this.montant = montant;
        this.prestataire = prestataire;
    }

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(Long abonnementId) {
        this.abonnementId = abonnementId;
    }

    public Long getMethodePaiementId() {
        return methodePaiementId;
    }

    public void setMethodePaiementId(Long methodePaiementId) {
        this.methodePaiementId = methodePaiementId;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
} 