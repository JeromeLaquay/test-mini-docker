package com.jeroka.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "historique_activites")
public class HistoriqueActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(name = "type_entite")
    private String typeEntite;

    @NotNull
    @Column(name = "id_entite")
    private Long idEntite;

    @NotBlank
    private String action;

    @Column(columnDefinition = "jsonb")
    private String details;

    @Column(name = "adresse_ip")
    private String adresseIP;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "date_action", nullable = false, updatable = false)
    private LocalDateTime dateAction;

    // Constructeurs
    public HistoriqueActivite() {
    }

    public HistoriqueActivite(User user, String typeEntite, Long idEntite, String action) {
        this.user = user;
        this.typeEntite = typeEntite;
        this.idEntite = idEntite;
        this.action = action;
        this.dateAction = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTypeEntite() {
        return typeEntite;
    }

    public Long getIdEntite() {
        return idEntite;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTypeEntite(String typeEntite) {
        this.typeEntite = typeEntite;
    }

    public void setIdEntite(Long idEntite) {
        this.idEntite = idEntite;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoriqueActivite that = (HistoriqueActivite) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(typeEntite, that.typeEntite) &&
                Objects.equals(idEntite, that.idEntite) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, typeEntite, idEntite, action);
    }

    // toString
    @Override
    public String toString() {
        return "HistoriqueActivite{" +
                "id=" + id +
                ", typeEntite='" + typeEntite + '\'' +
                ", idEntite=" + idEntite +
                ", action='" + action + '\'' +
                ", dateAction=" + dateAction +
                '}';
    }
} 