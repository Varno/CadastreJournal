package com.re.util;

import com.vaadin.data.Property;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;
import org.vaadin.peter.imagestrip.ImageStrip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MultiUpload extends MultiFileUpload implements Property.ValueChangeListener {
    private ImageStrip strip;
    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
    private List<File> imagesList = new ArrayList<File>();

    public MultiUpload() {
        setUploadButtonCaption("Загрузить Документ");
        setRootDirectory(TEMP_FILE_DIR);
        initImageStrip();
        setSizeFull();
    }

    protected boolean supportsFileDrops() {
        return false;
    }

    @Override
    protected void handleFile(final File file, String fileName,
                              String mimeType, long length) {
        try {
            if (length > 10000000) {
                Notification.show("Слишком большой.Разрешена загрузка файлов не более 10мб.");
                return;
            }

            String explicitMime = Files.probeContentType(file.getAbsoluteFile().toPath());

            if (explicitMime == null || !explicitMime.contains("image")) {
                Notification.show("Разрешена загрузка только изображений!");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //fixme:log it
        }
        FileResource resource = new FileResource(file);
        strip.addImage(resource);
        imagesList.add(file);
    }

    @Override
    protected FileBuffer createReceiver() {
        FileBuffer receiver = super.createReceiver();
                /*
                 * Make receiver not to delete files after they have been
                 * handled by #handleFile().
                 */
        receiver.setDeleteFiles(false);
        return receiver;
    }

    private void initImageStrip() {
        if (strip == null) {
            strip = new ImageStrip();
            strip.setWidth("100%");
            strip.setAnimated(true);
            strip.setSelectable(true);
            strip.setImageBoxWidth(140);
            strip.setImageBoxHeight(140);
            strip.setMaxAllowed(6);
            addComponent(strip);
        }
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        Notification.show("Value change event");
    }

    public List<File> getDocumentsList() {
        return imagesList;
    }
}
