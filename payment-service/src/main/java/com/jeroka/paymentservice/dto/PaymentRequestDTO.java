package com.jeroka.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;

    @NotNull(message = "La devise est obligatoire")
    @Size(min = 3, max = 3, message = "La devise doit être au format ISO (3 caractères)")
    private String currency;

    @NotNull(message = "L'ID de la méthode de paiement est obligatoire")
    private UUID paymentMethodId;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private UUID userId;

    @NotNull(message = "L'ID de la commande est obligatoire")
    private UUID orderId;

    private String paymentIntentId;
} 