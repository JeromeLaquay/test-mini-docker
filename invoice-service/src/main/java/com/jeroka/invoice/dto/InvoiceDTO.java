package com.jeroka.invoice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceDTO {
    private String id;
    private String invoiceNumber;
    private LocalDateTime createdAt;
    private String orderId;
    private LocalDateTime orderDate;
    
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    
    private List<InvoiceItemDTO> items;
    private BigDecimal subtotal;
    private double taxRate;
    private BigDecimal taxAmount;
    private BigDecimal total;
} 