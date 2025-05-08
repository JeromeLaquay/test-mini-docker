package com.jeroka.auth.model;

import com.jeroka.auth.model.enums.StatutTransaction;
import com.jeroka.auth.model.enums.TypeTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "methode_paiement_id")
    private MethodePaiement methodePaiement;

    @NotBlank
    @Column(unique = true)
    private String reference;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @NotNull
    @PositiveOrZero
    private BigDecimal montant;

    @NotBlank
    @Size(min = 3, max = 3)
    private String devise = "EUR";

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutTransaction statut;

    @NotBlank
    private String prestataire;

    @Column(name = "identifiant_externe")
    private String identifiantExterne;

    private String description;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "code_erreur")
    private String codeErreur;

    @Column(name = "message_erreur")
    private String messageErreur;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    private Facture facture;

    // Constructeurs
    public Transaction() {
    }

    public Transaction(User user, TypeTransaction type, BigDecimal montant, String reference, 
                      String prestataire, StatutTransaction statut) {
        this.user = user;
        this.type = type;
        this.montant = montant;
        this.reference = reference;
        this.prestataire = prestataire;
        this.statut = statut;
        this.dateTransaction = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public User getUser() {
        return user;
    }

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }

    public String getReference() {
        return reference;
    }

    public TypeTransaction getType() {
        return type;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public String getDevise() {
        return devise;
    }

    public StatutTransaction getStatut() {
        return statut;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public String getIdentifiantExterne() {
        return identifiantExterne;
    }

    public String getDescription() {
        return description;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getCodeErreur() {
        return codeErreur;
    }

    public String getMessageErreur() {
        return messageErreur;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public Facture getFacture() {
        return facture;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public void setStatut(StatutTransaction statut) {
        this.statut = statut;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public void setIdentifiantExterne(String identifiantExterne) {
        this.identifiantExterne = identifiantExterne;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }

    public void setMessageErreur(String messageErreur) {
        this.messageErreur = messageErreur;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(user, that.user) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, user, type);
    }

    // toString
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", type=" + type +
                ", montant=" + montant +
                ", devise='" + devise + '\'' +
                ", statut=" + statut +
                ", dateTransaction=" + dateTransaction +
                '}';
    }
} 