package com.jeroka.invoice.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceItem {
    private String description;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal total;
} 