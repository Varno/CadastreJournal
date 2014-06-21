package com.re.components.document;

import com.re.entity.REDocument;
import com.vaadin.server.FileResource;
import org.vaadin.peter.imagestrip.ImageStrip;

import java.util.List;

public class REDocumentGallery extends ImageStrip {
    public REDocumentGallery() {
        initLayout();
    }

    private void initLayout() {
        setWidth("100%");
        setAnimated(true);
        setSelectable(true);
        setImageBoxWidth(140);
        setImageBoxHeight(140);
        setMaxAllowed(6);
    }

    public void addDocsToGallery(List<REDocument> list) {
        for(REDocument reDocument: list){
            if(reDocument.getDocument() != null){
                FileResource resource = new FileResource(reDocument.getDocument());
                addImage(resource);
            }
        }
    }
}
