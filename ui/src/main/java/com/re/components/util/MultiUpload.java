package com.re.components.util;

import com.re.components.document.LoadedDocumentRow;
import com.re.entity.REDocument;
import com.re.entity.RealEstate;
import com.re.service.REDocumentService;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MultiUpload extends MultiFileUpload {
    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
    private List<REDocument> reDocumentsList = new ArrayList<REDocument>();
    private VerticalLayout loadedDocumentsLayout = new VerticalLayout();
    private REDocumentService reDocumentService;

    public MultiUpload(REDocumentService reDocumentService) {
        this.reDocumentService = reDocumentService;
        setUploadButtonCaption("Загрузить Документ");
        setRootDirectory(TEMP_FILE_DIR);
        setSizeFull();
        addComponent(loadedDocumentsLayout);
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
        REDocument reDocument = new REDocument();
        reDocument.setFileName(tempFile.getName());
        reDocument.setTempDocumentFile(tempFile);
        reDocumentsList.add(reDocument);
        addDocumentToDocementLayout(reDocument);
    }

    @Override
    protected FileBuffer createReceiver() {
        FileBuffer receiver = super.createReceiver();
        receiver.setDeleteFiles(false);
        return receiver;
    }

    public List<REDocument> getReDocumentsList() {
        return reDocumentsList;
    }

    public void addREDocumentsToForm(List<REDocument> list) {
        setReDocumentsList(list);
        for (final REDocument reDocument : list) {
            addDocumentToDocementLayout(reDocument);
        }

    }

    private void addDocumentToDocementLayout(final REDocument reDocument) {
        RealEstate rEstate = reDocument.getRealEstate();
        final boolean isREstateExists = rEstate != null;
        FileResource resource =  new FileResource(reDocument.getDocument());
        final LoadedDocumentRow newRow = new LoadedDocumentRow(reDocument.getFileName(), resource, isREstateExists);

        loadedDocumentsLayout.addComponent(newRow);
        newRow.getDeleteButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    loadedDocumentsLayout.removeComponent(newRow);
                    getReDocumentsList().remove(reDocument);
                    if (reDocument.getId() != null)
                        reDocumentService.delete(reDocument);
                } catch (IOException e) {
                    Notification.show("Не удалось удалить объект");
                }
            }
        });
    }

    public void setReDocumentsList(List<REDocument> reDocumentsList) {
        this.reDocumentsList = reDocumentsList;
    }
}
