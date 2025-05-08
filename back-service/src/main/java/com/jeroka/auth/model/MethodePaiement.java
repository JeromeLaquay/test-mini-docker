package com.jeroka.auth.model;

import com.jeroka.auth.model.enums.TypePaiement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "methodes_paiement")
public class MethodePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypePaiement type;

    @NotBlank
    private String token;

    @Column(name = "identifiant_externe")
    private String identifiantExterne;

    @Size(min = 4, max = 4)
    @Column(name = "derniers_chiffres")
    private String derniersChiffres;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    private String marque;

    @Column(name = "nom_titulaire")
    private String nomTitulaire;

    @NotNull
    @Column(name = "par_defaut")
    private Boolean parDefaut = false;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;

    // Constructeurs
    public MethodePaiement() {
    }

    public MethodePaiement(User user, TypePaiement type, String token) {
        this.user = user;
        this.type = type;
        this.token = token;
        this.parDefaut = false;
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

    public TypePaiement getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getIdentifiantExterne() {
        return identifiantExterne;
    }

    public String getDerniersChiffres() {
        return derniersChiffres;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public String getMarque() {
        return marque;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public Boolean getParDefaut() {
        return parDefaut;
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

    public void setType(TypePaiement type) {
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIdentifiantExterne(String identifiantExterne) {
        this.identifiantExterne = identifiantExterne;
    }

    public void setDerniersChiffres(String derniersChiffres) {
        this.derniersChiffres = derniersChiffres;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setNomTitulaire(String nomTitulaire) {
        this.nomTitulaire = nomTitulaire;
    }

    public void setParDefaut(Boolean parDefaut) {
        this.parDefaut = parDefaut;
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
        MethodePaiement that = (MethodePaiement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                type == that.type &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, type, token);
    }

    // toString
    @Override
    public String toString() {
        return "MethodePaiement{" +
                "id=" + id +
                ", type=" + type +
                ", derniersChiffres='" + derniersChiffres + '\'' +
                ", marque='" + marque + '\'' +
                ", parDefaut=" + parDefaut +
                '}';
    }
} 