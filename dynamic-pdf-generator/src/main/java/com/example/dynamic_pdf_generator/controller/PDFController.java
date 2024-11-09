package com.example.dynamic_pdf_generator.controller;

import com.example.dynamic_pdf_generator.model.Invoice;
import com.example.dynamic_pdf_generator.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody Invoice invoice){
        System.out.println("Received request to generate PDF");
        try {
            if (invoice == null || invoice.getItems() == null)
                throw new Exception("Required request body is missing");
            String pdfPath = pdfGeneratorService.generatePdfFormat(invoice);
            return new ResponseEntity<>("PDF generated successfully at: " + pdfPath, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error generating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam String path) throws IOException {
        File pdfFile = new File(path);
        if(pdfFile.exists()){
            FileInputStream fis = new FileInputStream(pdfFile);
            byte[] pdfBytes = fis.readAllBytes();
            fis.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName());
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
