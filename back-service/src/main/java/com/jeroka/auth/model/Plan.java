package com.jeroka.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    private String description;

    @NotNull
    @PositiveOrZero
    @Column(name = "prix_mensuel", nullable = false)
    private BigDecimal prixMensuel;

    @PositiveOrZero
    @Column(name = "prix_annuel")
    private BigDecimal prixAnnuel;

    @PositiveOrZero
    @Column(name = "periode_essai_jours")
    private Integer periodeEssaiJours;

    @NotNull
    @Column(nullable = false)
    private Boolean actif = true;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;

    @OneToMany(mappedBy = "plan")
    private List<Abonnement> abonnements = new ArrayList<>();

    // Constructeurs
    public Plan() {
    }

    public Plan(String nom, BigDecimal prixMensuel) {
        this.nom = nom;
        this.prixMensuel = prixMensuel;
        this.actif = true;
        this.dateCreation = LocalDateTime.now();
        this.dateMiseAJour = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrixMensuel() {
        return prixMensuel;
    }

    public BigDecimal getPrixAnnuel() {
        return prixAnnuel;
    }

    public Integer getPeriodeEssaiJours() {
        return periodeEssaiJours;
    }

    public Boolean getActif() {
        return actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateMiseAJour() {
        return dateMiseAJour;
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrixMensuel(BigDecimal prixMensuel) {
        this.prixMensuel = prixMensuel;
    }

    public void setPrixAnnuel(BigDecimal prixAnnuel) {
        this.prixAnnuel = prixAnnuel;
    }

    public void setPeriodeEssaiJours(Integer periodeEssaiJours) {
        this.periodeEssaiJours = periodeEssaiJours;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateMiseAJour(LocalDateTime dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
        this.abonnements = abonnements;
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id) &&
                Objects.equals(nom, plan.nom) &&
                Objects.equals(prixMensuel, plan.prixMensuel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prixMensuel);
    }

    // toString
    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prixMensuel=" + prixMensuel +
                ", actif=" + actif +
                '}';
    }
} 