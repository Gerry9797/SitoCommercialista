package com.commercialista.backend.services.rendering;

import com.lowagie.text.Image;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

public class ImageReplacedElementFactory implements ReplacedElementFactory {
    private final ReplacedElementFactory superFactory;

    public ImageReplacedElementFactory(ReplacedElementFactory superFactory) {
        this.superFactory = superFactory;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String nodeName = element.getNodeName();
        if ("img".equals(nodeName) && element.hasAttribute("src")) {
            byte[] imageBytes = null;
            String srcValue = element.getAttribute("src");
            if (srcValue.startsWith("data:image")) {
                // L'immagine è codificata in Base64
                String base64Data = srcValue.substring(srcValue.indexOf(",") + 1);
                imageBytes = Base64.getDecoder().decode(base64Data);
            } else {
                // L'immagine è un file regolare
                try (InputStream input = new FileInputStream("/base/folder/" + srcValue)) {
                    imageBytes = IOUtils.toByteArray(input);
                } catch (Exception e) {
                    throw new RuntimeException("Error reading the image file.", e);
                }
            }

            if (imageBytes != null) {
                try {
                    final Image image = Image.getInstance(imageBytes);
                    final FSImage fsImage = new ITextFSImage(image);
                    if (fsImage != null) {
                        if ((cssWidth != -1) || (cssHeight != -1)) {
                            fsImage.scale(cssWidth, cssHeight);
                        }
                        return new ITextImageElement(fsImage);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error creating the image.", e);
                }
            }
        }
        return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        this.superFactory.reset();
    }

    @Override
    public void remove(Element e) {
        this.superFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        this.superFactory.setFormSubmissionListener(listener);
    }
}