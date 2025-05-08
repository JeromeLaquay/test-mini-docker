package com.jeroka.auth.dto;

import com.jeroka.auth.model.enums.TypePaiement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MethodePaiementResponseDTO {
    private Long id;
    private Long userId;
    private TypePaiement type;
    private String identifiantExterne;
    private String derniersChiffres;
    private String dateExpiration;
    private String marque;
    private String nomTitulaire;
    private Boolean parDefaut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateMiseAJour;

    // Constructeurs
    public MethodePaiementResponseDTO() {
    }

    public MethodePaiementResponseDTO(Long id, Long userId, TypePaiement type, String derniersChiffres,
                                    String marque, Boolean parDefaut, LocalDateTime dateCreation) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.derniersChiffres = derniersChiffres;
        this.marque = marque;
        this.parDefaut = parDefaut;
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

    public TypePaiement getType() {
        return type;
    }

    public void setType(TypePaiement type) {
        this.type = type;
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