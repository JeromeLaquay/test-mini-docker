package com.jeroka.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FactureRequestDTO {
    @NotBlank
    private String numero;

    @NotNull
    private LocalDate dateEmission;

    @NotNull
    @PositiveOrZero
    private BigDecimal montantHT;

    @NotNull
    @PositiveOrZero
    private BigDecimal tauxTVA;

    @NotNull
    @PositiveOrZero
    private BigDecimal montantTTC;

    @NotNull
    private LocalDate datePaiement;

    private String urlPDF;

    private String notes;

    // Constructeurs
    public FactureRequestDTO() {
    }

    public FactureRequestDTO(String numero, LocalDate dateEmission, BigDecimal montantHT, BigDecimal tauxTVA, String notes) {
        this.numero = numero;
        this.dateEmission = dateEmission;
        this.montantHT = montantHT;
        this.tauxTVA = tauxTVA;
        this.notes = notes;
    }

    // Getters et Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public BigDecimal getMontantHT() {
        return montantHT;
    }

    public void setMontantHT(BigDecimal montantHT) {
        this.montantHT = montantHT;
    }

    public BigDecimal getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(BigDecimal tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 