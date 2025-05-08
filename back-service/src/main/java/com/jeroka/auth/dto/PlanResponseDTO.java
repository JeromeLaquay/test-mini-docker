package com.jeroka.auth.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlanResponseDTO {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prixMensuel;
    private BigDecimal prixAnnuel;
    private Integer periodeEssaiJours;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateMiseAJour;

    // Constructeurs
    public PlanResponseDTO() {
    }

    public PlanResponseDTO(Long id, String nom, BigDecimal prixMensuel, Boolean actif,
                         LocalDateTime dateCreation) {
        this.id = id;
        this.nom = nom;
        this.prixMensuel = prixMensuel;
        this.actif = actif;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrixMensuel() {
        return prixMensuel;
    }

    public void setPrixMensuel(BigDecimal prixMensuel) {
        this.prixMensuel = prixMensuel;
    }

    public BigDecimal getPrixAnnuel() {
        return prixAnnuel;
    }

    public void setPrixAnnuel(BigDecimal prixAnnuel) {
        this.prixAnnuel = prixAnnuel;
    }

    public Integer getPeriodeEssaiJours() {
        return periodeEssaiJours;
    }

    public void setPeriodeEssaiJours(Integer periodeEssaiJours) {
        this.periodeEssaiJours = periodeEssaiJours;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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