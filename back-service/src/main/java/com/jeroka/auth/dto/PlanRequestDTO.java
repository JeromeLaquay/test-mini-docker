package com.jeroka.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class PlanRequestDTO {
    @NotBlank
    private String nom;

    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal prixMensuel;

    @PositiveOrZero
    private BigDecimal prixAnnuel;

    @PositiveOrZero
    private Integer periodeEssaiJours;

    @NotNull
    private Boolean actif = true;

    // Constructeurs
    public PlanRequestDTO() {
    }

    public PlanRequestDTO(String nom, BigDecimal prixMensuel) {
        this.nom = nom;
        this.prixMensuel = prixMensuel;
        this.actif = true;
    }

    // Getters et Setters
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
} 