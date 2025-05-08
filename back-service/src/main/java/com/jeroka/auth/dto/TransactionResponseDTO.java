package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.StatutTransaction;
import com.jeroka.auth.model.enums.TypeTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private Long id;
    private Long userId;
    private Long abonnementId;
    private Long methodePaiementId;
    private String reference;
    private TypeTransaction type;
    private BigDecimal montant;
    private String devise;
    private StatutTransaction statut;
    private String prestataire;
    private String identifiantExterne;
    private String description;
    private String metadata;
    private String codeErreur;
    private String messageErreur;
    private LocalDateTime dateTransaction;
    private Long factureId;

    // Constructeurs
    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(Long id, Long userId, String reference, TypeTransaction type,
                                BigDecimal montant, String devise, StatutTransaction statut,
                                String prestataire, LocalDateTime dateTransaction) {
        this.id = id;
        this.userId = userId;
        this.reference = reference;
        this.type = type;
        this.montant = montant;
        this.devise = devise;
        this.statut = statut;
        this.prestataire = prestataire;
        this.dateTransaction = dateTransaction;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public StatutTransaction getStatut() {
        return statut;
    }

    public void setStatut(StatutTransaction statut) {
        this.statut = statut;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public String getIdentifiantExterne() {
        return identifiantExterne;
    }

    public void setIdentifiantExterne(String identifiantExterne) {
        this.identifiantExterne = identifiantExterne;
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

    public String getCodeErreur() {
        return codeErreur;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }

    public String getMessageErreur() {
        return messageErreur;
    }

    public void setMessageErreur(String messageErreur) {
        this.messageErreur = messageErreur;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }
} 