package com.re.components.util;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Uploader extends CustomComponent implements Upload.Receiver, Upload.ProgressListener,
        Upload.FailedListener, Upload.SucceededListener {

    ByteArrayOutputStream os = new ByteArrayOutputStream(10240);

    String filename;
    StreamResource.StreamSource source;
    ProgressBar progress = new ProgressBar(0.0f);
    Image image = new Image();

    public Uploader() {
        Upload upload = new Upload("", null);
        upload.setButtonCaption("Загрузить");
        upload.setReceiver(this);
        upload.addProgressListener(this);
        upload.addFailedListener(this);
        upload.addSucceededListener(this);
        // Put the upload and image display in a panel
        Panel panel = new Panel("Документы");
        panel.setWidth("100%");
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSpacing(true);
        panel.setContent(panelContent);
        panelContent.addComponent(upload);
        panelContent.addComponent(progress);
        panelContent.addComponent(image);

        progress.setVisible(false);
        image.setVisible(false);
        image.setWidth("10%");
        image.setHeight("10%");
        setCompositionRoot(panel);
    }

    public OutputStream receiveUpload(String filename, String mimeType) {
        this.filename = filename;
        os.reset();
        return os;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        progress.setVisible(true);
        if (contentLength == -1)
            progress.setIndeterminate(true);
        else {
            progress.setIndeterminate(false);
            progress.setValue(((float)readBytes) /
                    ((float)contentLength));
        }
    }

    public void uploadSucceeded(Upload.SucceededEvent event) {
        image.setVisible(true);
        source = new StreamResource.StreamSource() {
            public InputStream getStream() {
                return new ByteArrayInputStream(os.toByteArray());
            }
        };

        if (image.getSource() == null)
            // Create a new stream resource
            image.setSource(new StreamResource(source, filename));
        else { // Reuse the old resource
            StreamResource resource = (StreamResource) image.getSource();
            resource.setStreamSource(source);
            resource.setFilename(filename);
        }

        image.markAsDirty();
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        Notification.show("Загрузка завершена с ошибкой",
                Notification.Type.ERROR_MESSAGE);
    }

    public Image getImage() {
        return image;
    }

    public String getFilename() {
        return filename;
    }

    public StreamResource.StreamSource getSource() {
        return source;
    }

    public ByteArrayOutputStream getOs() {
        return os;
    }
}

