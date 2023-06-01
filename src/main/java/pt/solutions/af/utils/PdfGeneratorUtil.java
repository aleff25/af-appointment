package pt.solutions.af.utils;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pt.solutions.af.invoice.model.Invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class PdfGeneratorUtil {

    private SpringTemplateEngine templateEngine;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    public PdfGeneratorUtil(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public File generatePdfFromInvoice(Invoice invoice) {
        Context ctx = new Context();
        ctx.setVariable("invoice", invoice);
        String processedHtml = templateEngine.process("email/pdf/invoice", ctx);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processedHtml, baseUrl);
        renderer.layout();

        String fileName = UUID.randomUUID().toString();
        FileOutputStream os = null;
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf");
            os = new FileOutputStream(outputFile);
            renderer.createPDF(os, false);
            renderer.finishPDF();
            return outputFile;
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
