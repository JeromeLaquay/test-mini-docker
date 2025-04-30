package com.jeroka.paymentservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentResponseDTO {
    private UUID id;
    private BigDecimal amount;
    private String currency;
    private String status;
    private UUID paymentMethodId;
    private UUID userId;
    private UUID orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentIntentId;
    private String errorMessage;
} 