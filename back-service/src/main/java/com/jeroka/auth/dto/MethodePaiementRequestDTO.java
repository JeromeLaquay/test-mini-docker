package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.TypePaiement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MethodePaiementRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private TypePaiement type;

    @NotBlank
    private String token;

    private String identifiantExterne;

    @Size(min = 4, max = 4)
    private String derniersChiffres;

    private String dateExpiration; // format MM/YY

    private String marque;

    private String nomTitulaire;

    private Boolean parDefaut = false;

    // Constructeurs
    public MethodePaiementRequestDTO() {
    }

    public MethodePaiementRequestDTO(Long userId, TypePaiement type, String token) {
        this.userId = userId;
        this.type = type;
        this.token = token;
    }

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TypePaiement getType() {
        return type;
    }

    public void setType(TypePaiement type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdentifiantExterne() {
        return identifiantExterne;
    }

    public void setIdentifiantExterne(String identifiantExterne) {
        this.identifiantExterne = identifiantExterne;
    }

    public String getDerniersChiffres() {
        return derniersChiffres;
    }

    public void setDerniersChiffres(String derniersChiffres) {
        this.derniersChiffres = derniersChiffres;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public void setNomTitulaire(String nomTitulaire) {
        this.nomTitulaire = nomTitulaire;
    }

    public Boolean getParDefaut() {
        return parDefaut;
    }

    public void setParDefaut(Boolean parDefaut) {
        this.parDefaut = parDefaut;
    }
} 