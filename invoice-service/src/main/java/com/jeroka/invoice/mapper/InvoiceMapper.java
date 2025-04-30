package com.jeroka.invoice.mapper;

import com.jeroka.invoice.dto.InvoiceDTO;
import com.jeroka.invoice.dto.InvoiceItemDTO;
import com.jeroka.invoice.model.Invoice;
import com.jeroka.invoice.model.InvoiceItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    public InvoiceDTO toDTO(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setOrderId(invoice.getOrderId());
        dto.setOrderDate(invoice.getOrderDate());
        
        // Informations client
        dto.setCustomerName(invoice.getCustomerName());
        dto.setCustomerEmail(invoice.getCustomerEmail());
        dto.setCustomerAddress(invoice.getCustomerAddress());
        
        // Montants
        dto.setSubtotal(invoice.getSubtotal());
        dto.setTaxRate(invoice.getTaxRate());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setTotal(invoice.getTotal());
        
        // Items
        if (invoice.getItems() != null) {
            dto.setItems(mapItems(invoice.getItems()));
        }
        
        return dto;
    }

    public Invoice toEntity(InvoiceDTO dto) {
        if (dto == null) {
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setCreatedAt(dto.getCreatedAt());
        invoice.setOrderId(dto.getOrderId());
        invoice.setOrderDate(dto.getOrderDate());
        
        // Informations client
        invoice.setCustomerName(dto.getCustomerName());
        invoice.setCustomerEmail(dto.getCustomerEmail());
        invoice.setCustomerAddress(dto.getCustomerAddress());
        
        // Montants
        invoice.setSubtotal(dto.getSubtotal());
        invoice.setTaxRate(dto.getTaxRate());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setTotal(dto.getTotal());
        
        // Items
        if (dto.getItems() != null) {
            invoice.setItems(mapItemsToEntity(dto.getItems()));
        }
        
        return invoice;
    }

    public void updateEntityFromDto(Invoice invoice, InvoiceDTO dto) {
        if (dto == null) {
            return;
        }

        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setCreatedAt(dto.getCreatedAt());
        invoice.setOrderId(dto.getOrderId());
        invoice.setOrderDate(dto.getOrderDate());
        
        // Informations client
        invoice.setCustomerName(dto.getCustomerName());
        invoice.setCustomerEmail(dto.getCustomerEmail());
        invoice.setCustomerAddress(dto.getCustomerAddress());
        
        // Montants
        invoice.setSubtotal(dto.getSubtotal());
        invoice.setTaxRate(dto.getTaxRate());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setTotal(dto.getTotal());
        
        // Items
        if (dto.getItems() != null) {
            invoice.setItems(mapItemsToEntity(dto.getItems()));
        }
    }

    // Méthodes pour mapper les items
    private List<InvoiceItemDTO> mapItems(List<InvoiceItem> items) {
        return items.stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
    }

    private List<InvoiceItem> mapItemsToEntity(List<InvoiceItemDTO> dtos) {
        return dtos.stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());
    }

    private InvoiceItemDTO toItemDTO(InvoiceItem item) {
        if (item == null) {
            return null;
        }

        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setDescription(item.getDescription());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setQuantity(item.getQuantity());
        dto.setTotal(item.getTotal());
        return dto;
    }

    private InvoiceItem toItemEntity(InvoiceItemDTO dto) {
        if (dto == null) {
            return null;
        }

        InvoiceItem item = new InvoiceItem();
        item.setDescription(dto.getDescription());
        item.setUnitPrice(dto.getUnitPrice());
        item.setQuantity(dto.getQuantity());
        item.setTotal(dto.getTotal());
        return item;
    }
} 