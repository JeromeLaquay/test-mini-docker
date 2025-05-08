package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.StatutFacture;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FactureResponseDTO {
    private Long id;
    private Long transactionId;
    private Long userId;
    private String numero;
    private LocalDate dateEmission;
    private LocalDate datePaiement;
    private BigDecimal montantHT;
    private BigDecimal tauxTVA;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;
    private StatutFacture statut;
    private String urlPDF;
    private String notes;
    private LocalDateTime dateCreation;

    // Constructeur par défaut
    public FactureResponseDTO() {
    }

    // Constructeur pour les champs obligatoires
    public FactureResponseDTO(Long id, Long transactionId, Long userId, String numero,
                            LocalDate dateEmission, BigDecimal montantHT, BigDecimal tauxTVA,
                            BigDecimal montantTVA, BigDecimal montantTTC, StatutFacture statut) {
        this.id = id;
        this.transactionId = transactionId;
        this.userId = userId;
        this.numero = numero;
        this.dateEmission = dateEmission;
        this.montantHT = montantHT;
        this.tauxTVA = tauxTVA;
        this.montantTVA = montantTVA;
        this.montantTTC = montantTTC;
        this.statut = statut;
    }

    // Constructeur complet
    public FactureResponseDTO(Long id, Long transactionId, Long userId, String numero,
                            LocalDate dateEmission, LocalDate datePaiement, BigDecimal montantHT,
                            BigDecimal tauxTVA, BigDecimal montantTVA, BigDecimal montantTTC,
                            StatutFacture statut, String urlPDF, String notes, LocalDateTime dateCreation) {
        this(id, transactionId, userId, numero, dateEmission, montantHT, tauxTVA, montantTVA, montantTTC, statut);
        this.datePaiement = datePaiement;
        this.urlPDF = urlPDF;
        this.notes = notes;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters standards
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontantHT() {
        return montantHT;
    }

    public void setMontantHT(BigDecimal montantHT) {
        this.montantHT = montantHT;
    }

    public BigDecimal getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(BigDecimal tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public BigDecimal getMontantTVA() {
        return montantTVA;
    }

    public void setMontantTVA(BigDecimal montantTVA) {
        this.montantTVA = montantTVA;
    }

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
} 