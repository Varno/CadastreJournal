package com.re.util;

import com.vaadin.data.Property;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;
import org.vaadin.peter.imagestrip.ImageStrip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiUpload extends CustomComponent implements Property.ValueChangeListener {
    private ImageStrip strip;
    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
    private VerticalLayout componentsLayout = new VerticalLayout();
    private List<File> imagesList = new ArrayList<File>();

    public MultiUpload() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");
        componentsLayout.setSpacing(true);
        mainLayout.addComponent(componentsLayout);
        initMultiUploader();
        initImageStrip();
        setCompositionRoot(mainLayout);
    }

    private void initMultiUploader() {
        MultiFileUpload multiUploader = new MultiFileUpload() {
            @Override
            protected void handleFile(final File file, String fileName,
                                      String mimeType, long length) {
                if(length > 10000000){
                    Notification.show("Слишком большой.Разрешена загрузка файлов не более 10мб.");
                    return;
                }
                if (mimeType.equals("image/png")) {
                    Notification.show("Разрешена загрузка только изображений!");
                    return;
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
            @Override
            protected String getAreaText(){
                return "";
            }
        };
        multiUploader.setWidth("100%");
        multiUploader.setUploadButtonCaption("Загрузить");
        multiUploader.setRootDirectory(TEMP_FILE_DIR);
        componentsLayout.addComponent(multiUploader);
    }

    private void initImageStrip() {
        if(strip == null){
            strip = new ImageStrip();
            strip.setAnimated(true);
            strip.setSelectable(true);
            strip.setImageBoxWidth(140);
            strip.setImageBoxHeight(140);
            strip.setMaxAllowed(6);
            componentsLayout.addComponent(strip);
        }
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {

    }

    public List<File> getImagesList() {
        return imagesList;
    }
}
