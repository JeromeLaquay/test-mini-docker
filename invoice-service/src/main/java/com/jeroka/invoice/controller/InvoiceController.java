package com.jeroka.invoice.controller;

import com.jeroka.invoice.dto.InvoiceDTO;
import com.jeroka.invoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @GetMapping("/{invoiceId}/pdf")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable String invoiceId) {
        try {
            byte[] pdfContent = invoicePdfService.generateInvoicePdf(invoiceId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "facture-" + invoiceId + ".pdf");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public List<InvoiceDTO> getAll() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InvoiceDTO create(@RequestBody InvoiceDTO dto) {
        return invoiceService.createInvoice(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> update(@PathVariable Long id, @RequestBody InvoiceDTO dto) {
        return invoiceService.updateInvoice(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
} 