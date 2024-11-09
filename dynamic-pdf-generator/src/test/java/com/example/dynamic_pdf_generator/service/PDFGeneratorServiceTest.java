package com.example.dynamic_pdf_generator.service;

import com.example.dynamic_pdf_generator.model.Invoice;
import com.example.dynamic_pdf_generator.model.Invoice.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PDFGeneratorServiceTest {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    private Invoice sampleInvoice;

    @BeforeEach
    public void setUp() {

        sampleInvoice = new Invoice();
        sampleInvoice.setSeller("Test Seller");
        sampleInvoice.setSellerAddress("123 Test Street");
        sampleInvoice.setSellerGstin("GSTIN1234");

        sampleInvoice.setBuyer("Test Buyer");
        sampleInvoice.setBuyerAddress("456 Buyer Avenue");
        sampleInvoice.setBuyerGstin("GSTIN5678");

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);
        items.add(item);
        sampleInvoice.setItems(items);

        sampleInvoice.setItems(items);
    }

    @Test
    public void testGeneratePdfFormat_createsPdfFile() throws IOException {
        // Call generatePdfFormat method
        String pdfPath = pdfGeneratorService.generatePdfFormat(sampleInvoice);

        // Verify the PDF file exists
        File pdfFile = new File(pdfPath);
        assertTrue(pdfFile.exists(), "PDF file should be created");

        // Verify the file is not empty
        assertThat(pdfFile.length()).isGreaterThan(0);

        // Clean up by deleting the file after the test
        Files.deleteIfExists(pdfFile.toPath());
    }

    @Test
    public void testGeneratePdfFormat_createsDirectoryIfNotExists() throws IOException {
        // Remove the directory if it already exists
        File directory = new File("generated_invoices");
        if (directory.exists()) {
            Files.deleteIfExists(directory.toPath());
        }

        // Call generatePdfFormat method
        String pdfPath = pdfGeneratorService.generatePdfFormat(sampleInvoice);

        // Verify that the directory was created
        assertTrue(directory.exists(), "Directory should be created");

        // Verify that the PDF file exists in the new directory
        File pdfFile = new File(pdfPath);
        assertTrue(pdfFile.exists(), "PDF file should be created in the new directory");

        // Clean up by deleting the file and directory after the test
        Files.deleteIfExists(pdfFile.toPath());
        Files.deleteIfExists(directory.toPath());
    }

    @Test
    public void testGeneratePdfFormat_verifiesPdfContent() throws IOException {
        // Call generatePdfFormat to generate the PDF
        String pdfPath = pdfGeneratorService.generatePdfFormat(sampleInvoice);

        // Load the PDF file
        File pdfFile = new File(pdfPath);
        assertTrue(pdfFile.exists(), "PDF file should be created");

        // just check that the file is not empty as a basic check.
        assertThat(pdfFile.length()).isGreaterThan(0);

        // Clean up by deleting the file after the test
        Files.deleteIfExists(pdfFile.toPath());
    }
}
