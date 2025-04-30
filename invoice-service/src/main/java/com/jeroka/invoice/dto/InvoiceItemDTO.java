package com.jeroka.invoice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceItemDTO {
    private String description;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal total;
} 