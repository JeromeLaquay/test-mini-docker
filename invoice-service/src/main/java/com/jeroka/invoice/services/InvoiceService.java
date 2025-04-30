package com.jeroka.invoice.services;

import com.jeroka.invoice.dto.InvoiceDTO;
import com.jeroka.invoice.mapper.InvoiceMapper;
import com.jeroka.invoice.model.Invoice;
import com.jeroka.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InvoiceDTO> getInvoiceById(String id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDTO);
    }

    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        Invoice invoice = invoiceMapper.toEntity(dto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(savedInvoice);
    }

    public Optional<InvoiceDTO> updateInvoice(String id, InvoiceDTO dto) {
        return invoiceRepository.findById(id)
                .map(existingInvoice -> {
                    invoiceMapper.updateEntityFromDto(existingInvoice, dto);
                    Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
                    return invoiceMapper.toDTO(updatedInvoice);
                });
    }

    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }
}