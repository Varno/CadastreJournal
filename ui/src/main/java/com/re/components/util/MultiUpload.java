package com.re.components.util;

import com.re.components.document.REDocumentGallery;
import com.re.entity.REDocument;
import com.vaadin.data.Property;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;
import org.vaadin.peter.imagestrip.ImageStrip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MultiUpload extends MultiFileUpload{
    private REDocumentGallery reDocumentGallery;
    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
    private List<REDocument> reDocumentsList = new ArrayList<REDocument>();

    public MultiUpload() {
        setUploadButtonCaption("Загрузить Документ");
        setRootDirectory(TEMP_FILE_DIR);
        initREDocumentGallery();
        setSizeFull();
    }

    protected boolean supportsFileDrops() {
        return false;
    }

    @Override
    protected void handleFile(final File tempFile, String fileName,
                              String mimeType, long length) {
        try {
            if (length > 10000000) {
                Notification.show("Слишком большой.Разрешена загрузка файлов не более 10мб.");
                return;
            }

            String explicitMime = Files.probeContentType(tempFile.getAbsoluteFile().toPath());

            if (explicitMime == null || !explicitMime.contains("image")) {
                Notification.show("Разрешена загрузка только изображений!");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //fixme:log it
        }
        FileResource resource = new FileResource(tempFile);
        reDocumentGallery.addImage(resource);
        REDocument reDocument = new REDocument();
        reDocument.setFileName(tempFile.getName());
        reDocument.setTempDocumentFile(tempFile);
        reDocumentsList.add(reDocument);
    }

    @Override
    protected FileBuffer createReceiver() {
        FileBuffer receiver = super.createReceiver();
        receiver.setDeleteFiles(false);
        return receiver;
    }

    private void initREDocumentGallery() {
        if (reDocumentGallery == null) {
            reDocumentGallery = new REDocumentGallery();
            addComponent(reDocumentGallery);
        }
    }

    public List<REDocument> getReDocumentsList() {
        return reDocumentsList;
    }

    public void addREDocumentsToGallery(List<REDocument> list){
        setReDocumentsList(list);
        reDocumentGallery.addDocsToGallery(list);

    }

    public void setReDocumentsList(List<REDocument> reDocumentsList) {
        this.reDocumentsList = reDocumentsList;
    }
}
