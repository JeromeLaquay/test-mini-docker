package com.jeroka.paymentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false, length = 3)
    private String currency;
    
    @Column(nullable = false, length = 20)
    private String status;
    
    @Column(name = "payment_method_id", nullable = false)
    private UUID paymentMethodId;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "payment_intent_id")
    private String paymentIntentId;
    
    @Column(name = "error_message")
    private String errorMessage;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 