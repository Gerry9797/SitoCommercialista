package com.commercialista.backend.services;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.pdfbox.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.commercialista.backend.services.rendering.AutoFont;
import com.commercialista.backend.services.rendering.ChainingReplacedElementFactory;
import com.commercialista.backend.services.rendering.ImageReplacedElementFactory;
import com.commercialista.backend.services.rendering.SVGReplacedElementFactory;
import com.commercialista.backend.services.rendering.AutoFont.CSSFont;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.PdfAConformance;

@Service
public class PdfService {

    private final VelocityEngine velocityEngine;
    private final DateTimeFormatter dateFormatter;

    public PdfService() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public String renderTemplate(String templateName, Map<String, Object> templateParameters) {
        // utils dependency
        templateParameters.put("dateFormatter", dateFormatter);
        
        VelocityContext context = new VelocityContext(templateParameters);
        Template template = velocityEngine.getTemplate("templates/" + templateName + ".vm");
        StringBuilder renderedTemplate = new StringBuilder();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        renderedTemplate.append(writer);
        return renderedTemplate.toString();
    }

    private byte[] convertHtmlToPdf(String html) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        html = html.replaceAll("<br>", "<br/>");
        // Input
        Document inputDoc = builder.parse(new ByteArrayInputStream(html.getBytes()));
        
        ITextRenderer renderer = new ITextRenderer();

        // Add external fonts
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-Black.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-Bold.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-BoldItalic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-ExtraLight.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-ExtraLightItalic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-Italic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-Light.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-LightItalic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-Regular.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-SemiBold.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("fonts/TitilliumWeb-SemiBoldItalic.ttf", BaseFont.IDENTITY_H, true);
        
        ChainingReplacedElementFactory chainingReplacedElementFactory = new ChainingReplacedElementFactory();
        chainingReplacedElementFactory.addReplacedElementFactory(new SVGReplacedElementFactory());
        chainingReplacedElementFactory.addReplacedElementFactory(new ImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory()));
        renderer.getSharedContext().setReplacedElementFactory(chainingReplacedElementFactory);
        
        renderer.setDocument(inputDoc, "");
        renderer.layout();
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        renderer.createPDF(output);
        
        // Output
        return output.toByteArray();
    }

    public byte[] convertPdfToPdfA(byte[] pdf) throws IOException {

        ICC_Profile icc = ICC_Profile.getInstance(ColorSpace.CS_sRGB);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (com.lowagie.text.Document document = new com.lowagie.text.Document();
             PdfReader reader = new PdfReader(new ByteArrayInputStream(pdf))) {

            PdfCopy pdfCopy = new PdfCopy(document, outputStream);
            pdfCopy.setPDFXConformance(PdfWriter.PDFA1B);
            pdfCopy.createXmpMetadata();

            document.open();
            pdfCopy.setOutputIntents("", "", "", "", icc);

            for (int i = 1; i <= reader.getNumberOfPages(); i++)
                pdfCopy.addPage(pdfCopy.getImportedPage(reader, i));
        }

        return outputStream.toByteArray();
    }

    public byte[] generatePdf(String templateName, Map<String, Object> templateParameters) throws Exception {

        String html = renderTemplate(templateName, templateParameters);
        byte[] pdf = convertHtmlToPdf(html);
        return convertPdfToPdfA(pdf);
    }
    
    public byte[] generateAccessiblePdf(String templateName, Map<String, Object> templateParameters) throws Exception {

    	PdfAConformance pdfaLevel = PdfAConformance.PDFA_3_A;
    	
        String html = renderTemplate(templateName, templateParameters);
        try (ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(html.getBytes("UTF-8"))) { 
            // Parse the XML content 
            W3CDom w3cDom = new W3CDom(); 
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(xmlInputStream, "UTF-8", ""); 
 
            URL fontDirURL = PdfService.class.getClassLoader().getResource("fonts"); 
            if (fontDirURL == null) { 
                throw new RuntimeException("Font directory not found in resources."); 
            } 
            Path fontDirectory = Paths.get(fontDirURL.toURI()); 
            List<CSSFont> fonts = AutoFont.findFontsInDirectory(fontDirectory);
 
            PdfRendererBuilder builder = new PdfRendererBuilder(); 
            AutoFont.toBuilder(builder, fonts); 
 
            builder.useFastMode(); 
            builder.usePdfUaAccessbility(true); 
            builder.usePdfAConformance(pdfaLevel); 
            builder.withW3cDocument(w3cDom.fromJsoup(jsoupDoc), ""); 
 
            try (InputStream colorProfile = PdfService.class.getResourceAsStream("/colorspaces/sRGB.icc")) { 
                byte[] colorProfileBytes = IOUtils.toByteArray(colorProfile); 
                builder.useColorProfile(colorProfileBytes); 
            } 
 
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
 
            builder.toStream(baos); 
            builder.run(); 
 
            return baos.toByteArray(); 
        }
    }

}
