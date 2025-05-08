package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.PeriodeAbonnement;
import com.jeroka.auth.model.enums.StatutAbonnement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AbonnementResponseDTO {
    private Long id;
    private Long userId;
    private Long planId;
    private Long methodePaiementId;
    private StatutAbonnement statut;
    private PeriodeAbonnement periode;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFinEssai;
    private LocalDateTime dateProchaineFacturation;
    private LocalDateTime dateFin;
    private BigDecimal montant;
    private Boolean autoRenouvellement;
    private String raisonAnnulation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateMiseAJour;

    // Constructeurs
    public AbonnementResponseDTO() {
    }

    public AbonnementResponseDTO(Long id, Long userId, Long planId, StatutAbonnement statut,
                               PeriodeAbonnement periode, LocalDateTime dateDebut, BigDecimal montant,
                               Boolean autoRenouvellement, LocalDateTime dateCreation) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.statut = statut;
        this.periode = periode;
        this.dateDebut = dateDebut;
        this.montant = montant;
        this.autoRenouvellement = autoRenouvellement;
        this.dateCreation = dateCreation;
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

    public StatutAbonnement getStatut() {
        return statut;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
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

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateMiseAJour() {
        return dateMiseAJour;
    }

    public void setDateMiseAJour(LocalDateTime dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }
} 