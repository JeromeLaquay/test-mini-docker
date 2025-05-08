package com.jeroka.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HistoriqueActiviteRequestDTO {
    private Long userId;

    @NotBlank
    private String typeEntite;

    @NotNull
    private Long idEntite;

    @NotBlank
    private String action;

    private String details;

    private String adresseIP;

    private String userAgent;

    // Constructeurs
    public HistoriqueActiviteRequestDTO() {
    }

    public HistoriqueActiviteRequestDTO(String typeEntite, Long idEntite, String action) {
        this.typeEntite = typeEntite;
        this.idEntite = idEntite;
        this.action = action;
    }

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypeEntite() {
        return typeEntite;
    }

    public void setTypeEntite(String typeEntite) {
        this.typeEntite = typeEntite;
    }

    public Long getIdEntite() {
        return idEntite;
    }

    public void setIdEntite(Long idEntite) {
        this.idEntite = idEntite;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
} 