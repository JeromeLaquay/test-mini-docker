package com.jeroka.auth.model;

import com.jeroka.auth.model.enums.PeriodeAbonnement;
import com.jeroka.auth.model.enums.StatutAbonnement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "abonnements")
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "methode_paiement_id")
    private MethodePaiement methodePaiement;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PeriodeAbonnement periode;

    @NotNull
    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin_essai")
    private LocalDateTime dateFinEssai;

    @Column(name = "date_prochaine_facturation")
    private LocalDateTime dateProchaineFacturation;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @NotNull
    @PositiveOrZero
    private BigDecimal montant;

    @NotNull
    @Column(name = "auto_renouvellement")
    private Boolean autoRenouvellement = true;

    @Column(name = "raison_annulation")
    private String raisonAnnulation;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;

    // Constructeurs
    public Abonnement() {
    }

    public Abonnement(User user, Plan plan, MethodePaiement methodePaiement, StatutAbonnement statut, 
                     PeriodeAbonnement periode, LocalDateTime dateDebut, BigDecimal montant) {
        this.user = user;
        this.plan = plan;
        this.methodePaiement = methodePaiement;
        this.statut = statut;
        this.periode = periode;
        this.dateDebut = dateDebut;
        this.montant = montant;
        this.autoRenouvellement = true;
        this.dateCreation = LocalDateTime.now();
        this.dateMiseAJour = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Plan getPlan() {
        return plan;
    }

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }

    public StatutAbonnement getStatut() {
        return statut;
    }

    public PeriodeAbonnement getPeriode() {
        return periode;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public LocalDateTime getDateFinEssai() {
        return dateFinEssai;
    }

    public LocalDateTime getDateProchaineFacturation() {
        return dateProchaineFacturation;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public Boolean getAutoRenouvellement() {
        return autoRenouvellement;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateMiseAJour() {
        return dateMiseAJour;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }

    public void setPeriode(PeriodeAbonnement periode) {
        this.periode = periode;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFinEssai(LocalDateTime dateFinEssai) {
        this.dateFinEssai = dateFinEssai;
    }

    public void setDateProchaineFacturation(LocalDateTime dateProchaineFacturation) {
        this.dateProchaineFacturation = dateProchaineFacturation;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setAutoRenouvellement(Boolean autoRenouvellement) {
        this.autoRenouvellement = autoRenouvellement;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateMiseAJour(LocalDateTime dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonnement that = (Abonnement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(plan, that.plan) &&
                statut == that.statut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, plan, statut);
    }

    // toString
    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", statut=" + statut +
                ", periode=" + periode +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", montant=" + montant +
                '}';
    }
} 