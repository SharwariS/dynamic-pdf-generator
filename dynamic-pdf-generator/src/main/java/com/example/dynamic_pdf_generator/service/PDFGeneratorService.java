package com.example.dynamic_pdf_generator.service;

import com.example.dynamic_pdf_generator.model.Invoice;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PDFGeneratorService {

    private static final String DIRECTORY_PATH = "generated_invoices";

    public String generatePdfFormat(Invoice invoice) throws IOException {
        String pdfPath = setupDirectory(invoice.getSeller());

        try (FileOutputStream outputStream = new FileOutputStream(pdfPath);
             PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            document.add(createHeader(invoice));
            document.add(createItemsTable(invoice));

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error generating PDF", e);
        }

        return pdfPath;
    }

    // Sets up the directory and returns the full path for the PDF file
    private String setupDirectory(String sellerName) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return DIRECTORY_PATH + "/" + sellerName + "_invoice.pdf";
    }

    // Creates the header table with seller and buyer information
    private Table createHeader(Invoice invoice) {
        float[] columnWidths = {280F, 280F};
        Table headerTable = new Table(columnWidths);

        Color borderColor = new DeviceRgb(0, 0, 0); // Black border
        Border border = new SolidBorder(borderColor, 1);

        headerTable.addCell(createCell("Seller:\n" + invoice.getSeller() + "\n" + invoice.getSellerAddress() +
                "\nGSTIN: " + invoice.getSellerGstin(), border, 30));
        headerTable.addCell(createCell("Buyer:\n" + invoice.getBuyer() + "\n" + invoice.getBuyerAddress() +
                "\nGSTIN: " + invoice.getBuyerGstin(), border, 30));

        return headerTable;
    }

    // Creates a table for item details with header and rows of item data
    private Table createItemsTable(Invoice invoice) {
        float[] columnWidths = {200F, 120F, 120F, 120F};
        Table itemsTable = new Table(columnWidths);
        itemsTable.setTextAlignment(TextAlignment.CENTER);

        // Add header cells for item details
        itemsTable.addCell(createCell("Item", 5));
        itemsTable.addCell(createCell("Quantity", 5));
        itemsTable.addCell(createCell("Rate", 5));
        itemsTable.addCell(createCell("Amount", 5));

        // Add each item as a row in the items table
        for (Invoice.Item item : invoice.getItems()) {
            itemsTable.addCell(new Cell().add(new Paragraph(item.getName())));
            itemsTable.addCell(new Cell().add(new Paragraph(item.getQuantity())));
            itemsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))));
            itemsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))));
        }

        return itemsTable;
    }

    // Helper method to create a cell with custom padding and border
    private Cell createCell(String content, Border border, float padding) {
        return new Cell()
                .add(new Paragraph(content))
                .setBorder(border)
                .setPadding(padding);
    }

    // Helper method to create a cell (for header cells)
    private Cell createCell(String content, float padding) {
        return new Cell()
                .add(new Paragraph(content))
                .setPadding(padding);
    }
}
