package com.jeroka.invoice.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Invoice {
    private String id;
    private String invoiceNumber;
    private LocalDateTime createdAt;
    private String orderId;
    private LocalDateTime orderDate;
    
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    
    private List<InvoiceItem> items;
    private BigDecimal subtotal;
    private double taxRate;
    private BigDecimal taxAmount;
    private BigDecimal total;
} 