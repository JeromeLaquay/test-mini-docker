package com.jeroka.auth.model;

import com.jeroka.auth.model.enums.StatutFacture;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(unique = true)
    private String numero;

    @NotNull
    @Column(name = "date_emission")
    private LocalDate dateEmission;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @NotNull
    @PositiveOrZero
    @Column(name = "montant_ht")
    private BigDecimal montantHT;

    @NotNull
    @PositiveOrZero
    @Column(name = "taux_tva")
    private BigDecimal tauxTVA;

    @NotNull
    @PositiveOrZero
    @Column(name = "montant_tva")
    private BigDecimal montantTVA;

    @NotNull
    @PositiveOrZero
    @Column(name = "montant_ttc")
    private BigDecimal montantTTC;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    @Column(name = "url_pdf")
    private String urlPDF;

    private String notes;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    // Constructeurs
    public Facture() {
    }

    public Facture(Transaction transaction, User user, String numero, LocalDate dateEmission,
                  BigDecimal montantHT, BigDecimal tauxTVA) {
        this.transaction = transaction;
        this.user = user;
        this.numero = numero;
        this.dateEmission = dateEmission;
        this.montantHT = montantHT;
        this.tauxTVA = tauxTVA;
        this.montantTVA = montantHT.multiply(tauxTVA);
        this.montantTTC = montantHT.add(this.montantTVA);
        this.statut = StatutFacture.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public User getUser() {
        return user;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public BigDecimal getMontantHT() {
        return montantHT;
    }

    public BigDecimal getTauxTVA() {
        return tauxTVA;
    }

    public BigDecimal getMontantTVA() {
        return montantTVA;
    }

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setMontantHT(BigDecimal montantHT) {
        this.montantHT = montantHT;
        this.montantTVA = montantHT.multiply(this.tauxTVA);
        this.montantTTC = montantHT.add(this.montantTVA);
    }

    public void setTauxTVA(BigDecimal tauxTVA) {
        this.tauxTVA = tauxTVA;
        this.montantTVA = this.montantHT.multiply(tauxTVA);
        this.montantTTC = this.montantHT.add(this.montantTVA);
    }

    public void setMontantTVA(BigDecimal montantTVA) {
        this.montantTVA = montantTVA;
        this.montantTTC = this.montantHT.add(montantTVA);
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facture facture = (Facture) o;
        return Objects.equals(id, facture.id) &&
                Objects.equals(numero, facture.numero) &&
                Objects.equals(transaction, facture.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, transaction);
    }

    // toString
    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", dateEmission=" + dateEmission +
                ", montantHT=" + montantHT +
                ", montantTTC=" + montantTTC +
                ", statut=" + statut +
                '}';
    }
} 