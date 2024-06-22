package com.commercialista.backend.services;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.pdfbox.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.PdfAConformance;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);
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

    public byte[] generateAccessiblePdf(String templateName, Map<String, Object> templateParameters, String titoloPdf, String lingua) throws Exception {

        PdfAConformance pdfaLevel = PdfAConformance.PDFA_3_A;

        // cofigurazione metadati pdf
        templateParameters.put("lingua", lingua);
        templateParameters.put("titoloPdf", titoloPdf);

        String html = renderTemplate(templateName, templateParameters);
        String htmlWithBookmarks = addBookmarks(html);
        try (ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(htmlWithBookmarks.getBytes(StandardCharsets.UTF_8))) {
            // Parse the XML content
            W3CDom w3cDom = new W3CDom();
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(xmlInputStream, "UTF-8", "");

            PdfRendererBuilder builder = new PdfRendererBuilder();

            List<String> fontsName = List.of(
                    "TitilliumWeb-Black.ttf",
                    "TitilliumWeb-Bold.ttf",
                    "TitilliumWeb-BoldItalic.ttf",
                    "TitilliumWeb-ExtraLight.ttf",
                    "TitilliumWeb-ExtraLightItalic.ttf",
                    "TitilliumWeb-Italic.ttf",
                    "TitilliumWeb-Light.ttf",
                    "TitilliumWeb-LightItalic.ttf",
                    "TitilliumWeb-Regular.ttf",
                    "TitilliumWeb-SemiBold.ttf",
                    "TitilliumWeb-SemiBoldItalic.ttf"
            );

            loadFontsFromClasspath(builder, "fonts", fontsName);

            builder.useFastMode();
            builder.usePdfUaAccessbility(true);
            builder.usePdfAConformance(pdfaLevel);
            builder.withW3cDocument(w3cDom.fromJsoup(jsoupDoc), "");

            try (InputStream colorProfile = new ClassPathResource("colorspaces/sRGB.icc").getInputStream()) {
                byte[] colorProfileBytes = IOUtils.toByteArray(colorProfile);
                builder.useColorProfile(colorProfileBytes);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            builder.toStream(baos);
            builder.run();

            return baos.toByteArray();
        }
    }

    private void loadFontsFromClasspath(PdfRendererBuilder builder, String fontsDir, List<String> fontsName) throws IOException, FontFormatException {

        for (String fontFileName : fontsName) {
            String fontPath = fontsDir + "/" + fontFileName;
            try (InputStream fontInputStream = new ClassPathResource(fontPath).getInputStream()) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);

                String family = font.getFamily();
                String name = font.getFontName(Locale.US).toLowerCase(Locale.US);
                int weight = 400;

                if (name.contains("thin")) {
                    weight = 100;
                } else if (name.contains("light")) {
                    if (name.contains("extra")) {
                        weight = 200;
                    } else {
                        weight = 300;
                    }
                } else if (name.contains("medium")) {
                    weight = 500;
                } else if (name.contains("bold")) {
                    if (name.contains("semi")) {
                        weight = 600;
                    } else if (name.contains("extra")) {
                        weight = 800;
                    } else {
                        weight = 700;
                    }
                } else if (name.contains("black")) {
                    weight = 900;
                }

                BaseRendererBuilder.FontStyle style = name.contains("italic") ? BaseRendererBuilder.FontStyle.ITALIC : BaseRendererBuilder.FontStyle.NORMAL;

                builder.useFont(() -> {
                    try {
                        return new ClassPathResource(fontPath).getInputStream();
                    } catch (IOException e) {
                        logger.error("Caricamento del font non riuscito: " + fontPath);
                        throw new RuntimeException(e);
                    }
                }, family, weight, style, true);

            }
        }
    }


    private String addBookmarks(String html) {
        // Parse the HTML content with Jsoup
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        // Find all header tags (h1 to h5)
        Elements headerTags = document.select("h1, h2, h3, h4, h5");
        // Create the bookmarks element
        Element bookmarksElement = document.createElement("bookmarks");

        // Iterate over each header tag
        for (Element header : headerTags) {
            // Ensure the header has an id
            if (header.id().isEmpty()) {
                // Generate a random id
                String randomId = "id-" + UUID.randomUUID().toString();
                header.attr("id", randomId);
            }

            // Extract the text from the header and its children
            String headerText = header.text();

            // Create the bookmark element
            Element bookmark = document.createElement("bookmark");
            bookmark.attr("href", "#" + header.id());
            bookmark.attr("name", headerText);

            // Add the bookmark to the bookmarks element
            bookmarksElement.appendChild(bookmark);
        }

        // Insert the bookmarks element at the beginning of the body
        document.body().prependChild(bookmarksElement);

        // Return the modified HTML
        return document.html();
    }

}
