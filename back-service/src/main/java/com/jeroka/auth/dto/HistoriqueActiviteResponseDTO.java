package com.jeroka.auth.dto;

import java.time.LocalDateTime;

public class HistoriqueActiviteResponseDTO {
    private Long id;
    private Long userId;
    private String typeEntite;
    private Long idEntite;
    private String action;
    private String details;
    private String adresseIP;
    private String userAgent;
    private LocalDateTime dateAction;

    // Constructeurs
    public HistoriqueActiviteResponseDTO() {
    }

    public HistoriqueActiviteResponseDTO(Long id, Long userId, String typeEntite,
                                       Long idEntite, String action, LocalDateTime dateAction) {
        this.id = id;
        this.userId = userId;
        this.typeEntite = typeEntite;
        this.idEntite = idEntite;
        this.action = action;
        this.dateAction = dateAction;
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

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }
} 