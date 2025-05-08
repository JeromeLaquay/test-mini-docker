package com.jeroka.auth.dto;

import com.jeroka.auth.model.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String codePostal;
    private String ville;
    private String pays;
    private Set<Role> roles = new HashSet<>();
    private List<Long> methodesPaiement;
    private List<Long> abonnements;
    private LocalDateTime dateCreation;
    private LocalDateTime dateMiseAJour;

    // Constructeur par défaut
    public UserResponseDTO() {
    }

    // Constructeur avec champs obligatoires
    public UserResponseDTO(Long id, String username, String email, String nom, String prenom) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Constructeur avec roles
    public UserResponseDTO(Long id, String username, String email, String nom, String prenom, Set<Role> roles) {
        this(id, username, email, nom, prenom);
        this.roles = roles != null ? roles : new HashSet<>();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles != null ? roles : new HashSet<>();
    }

    public List<Long> getMethodesPaiement() {
        return methodesPaiement;
    }

    public void setMethodesPaiement(List<Long> methodesPaiement) {
        this.methodesPaiement = methodesPaiement;
    }

    public List<Long> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Long> abonnements) {
        this.abonnements = abonnements;
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