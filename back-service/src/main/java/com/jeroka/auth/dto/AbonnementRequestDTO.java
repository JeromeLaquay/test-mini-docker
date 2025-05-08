package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.PeriodeAbonnement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AbonnementRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long planId;

    private Long methodePaiementId;

    @NotNull
    private PeriodeAbonnement periode;

    @NotNull
    private LocalDateTime dateDebut;

    private LocalDateTime dateFinEssai;

    @NotNull
    @PositiveOrZero
    private BigDecimal montant;

    @NotNull
    private Boolean autoRenouvellement = true;

    private LocalDateTime dateProchaineFacturation;
    private LocalDateTime dateFin;
    private String commentaire;

    // Constructeurs
    public AbonnementRequestDTO() {
    }

    public AbonnementRequestDTO(Long userId, Long planId, PeriodeAbonnement periode,
                              LocalDateTime dateDebut, BigDecimal montant) {
        this.userId = userId;
        this.planId = planId;
        this.periode = periode;
        this.dateDebut = dateDebut;
        this.montant = montant;
    }

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getMethodePaiementId() {
        return methodePaiementId;
    }

    public void setMethodePaiementId(Long methodePaiementId) {
        this.methodePaiementId = methodePaiementId;
    }

    public PeriodeAbonnement getPeriode() {
        return periode;
    }

    public void setPeriode(PeriodeAbonnement periode) {
        this.periode = periode;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFinEssai() {
        return dateFinEssai;
    }

    public void setDateFinEssai(LocalDateTime dateFinEssai) {
        this.dateFinEssai = dateFinEssai;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Boolean getAutoRenouvellement() {
        return autoRenouvellement;
    }

    public void setAutoRenouvellement(Boolean autoRenouvellement) {
        this.autoRenouvellement = autoRenouvellement;
    }

    public LocalDateTime getDateProchaineFacturation() {
        return dateProchaineFacturation;
    }

    public void setDateProchaineFacturation(LocalDateTime dateProchaineFacturation) {
        this.dateProchaineFacturation = dateProchaineFacturation;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
} 