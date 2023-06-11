package pt.solutions.af.invoice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.invoice.application.InvoiceApplicationService;
import pt.solutions.af.invoice.model.Invoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/invoices")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class InvoiceController {

    @Autowired
    private InvoiceApplicationService invoiceService;

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> showAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/generate")
    public ResponseEntity<Void> issueInvoicesForConfirmedAppointments() {
        invoiceService.issueInvoicesForConfirmedAppointments();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{invoiceId}")
    public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable("invoiceId") String invoiceId) {
        try {
            File invoicePdf = invoiceService.generatePdfForInvoice(invoiceId);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(invoicePdf.length());
            respHeaders.setContentDispositionFormData("attachment", invoicePdf.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(invoicePdf));
            return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            log.error("Error while generating pdf for download, error: {} ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
