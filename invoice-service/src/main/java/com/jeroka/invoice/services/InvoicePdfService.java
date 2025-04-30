package com.jeroka.invoice.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.colors.ColorConstants;
import com.jeroka.invoice.dto.InvoiceDTO;
import com.jeroka.invoice.model.Invoice;
import com.jeroka.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoicePdfService {
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] generateInvoicePdf(String invoiceId) throws Exception {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
        if (optionalInvoice.isEmpty()) {
            throw new RuntimeException("Facture non trouvée");
        }
        
        Invoice invoice = optionalInvoice.get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            // En-tête
            addHeader(document, invoice);
            
            // Informations client
            addCustomerInfo(document, invoice);
            
            // Détails de la facture
            addInvoiceDetails(document, invoice);
            
            // Tableau des articles
            addItemsTable(document, invoice);
            
            // Total et sous-total
            addTotals(document, invoice);
            
            // Pied de page
            addFooter(document);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private void addHeader(Document document, Invoice invoice) {
        Paragraph header = new Paragraph("FACTURE")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        document.add(header);

        Paragraph invoiceNumber = new Paragraph("Facture N° " + invoice.getInvoiceNumber())
                .setFontSize(12);
        document.add(invoiceNumber);

        Paragraph date = new Paragraph("Date: " + invoice.getCreatedAt().format(DATE_FORMATTER))
                .setFontSize(12);
        document.add(date);
    }

    private void addCustomerInfo(Document document, Invoice invoice) {
        document.add(new Paragraph("\nInformations Client:").setBold());
        document.add(new Paragraph("Nom: " + invoice.getCustomerName()));
        document.add(new Paragraph("Adresse: " + invoice.getCustomerAddress()));
        document.add(new Paragraph("Email: " + invoice.getCustomerEmail()));
    }

    private void addInvoiceDetails(Document document, Invoice invoice) {
        document.add(new Paragraph("\nDétails de la commande:").setBold());
        document.add(new Paragraph("Commande N°: " + invoice.getOrderId()));
        document.add(new Paragraph("Date de commande: " + invoice.getOrderDate().format(DATE_FORMATTER)));
    }

    private void addItemsTable(Document document, Invoice invoice) {
        Table table = new Table(new float[]{3, 2, 2, 2});
        table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

        // En-têtes du tableau
        table.addHeaderCell(createHeaderCell("Description"));
        table.addHeaderCell(createHeaderCell("Prix unitaire"));
        table.addHeaderCell(createHeaderCell("Quantité"));
        table.addHeaderCell(createHeaderCell("Total"));

        // Lignes du tableau
        invoice.getItems().forEach(item -> {
            table.addCell(new Cell().add(new Paragraph(item.getDescription())));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f €", item.getUnitPrice()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f €", item.getTotal()))));
        });

        document.add(table);
    }

    private Cell createHeaderCell(String text) {
        return new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBold()
                .add(new Paragraph(text));
    }

    private void addTotals(Document document, Invoice invoice) {
        Table totalsTable = new Table(new float[]{3, 1});
        totalsTable.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(50));
        totalsTable.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT);

        totalsTable.addCell(new Cell().add(new Paragraph("Sous-total:")));
        totalsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f €", invoice.getSubtotal()))));

        totalsTable.addCell(new Cell().add(new Paragraph("TVA (" + invoice.getTaxRate() + "%):")));
        totalsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f €", invoice.getTaxAmount()))));

        totalsTable.addCell(new Cell().setBold().add(new Paragraph("Total:")));
        totalsTable.addCell(new Cell().setBold().add(new Paragraph(String.format("%.2f €", invoice.getTotal()))));

        document.add(totalsTable);
    }

    private void addFooter(Document document) {
        document.add(new Paragraph("\n\nMerci de votre confiance!")
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setItalic());
    }
} 