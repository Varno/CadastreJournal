package com.re.components.realestate;


import com.re.entity.REDocument;
import com.re.entity.RealEstate;
import com.re.service.REDocumentService;
import com.re.service.REDocumentServiceImpl;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;

import java.io.IOException;

public class REItemCardTabDocuments extends HorizontalLayout {
    private RealEstate realEstate;
    private REDocumentService reDocumentService;

    public REItemCardTabDocuments(RealEstate realEstate, REDocumentService reDocumentService) {
        this.realEstate = realEstate;
        this.reDocumentService = reDocumentService;
        setSizeFull();
        showImages();

    }

    private void showImages() {
        for(final REDocument reDocument: realEstate.getReDocumentList()){
            final VerticalLayout box = new VerticalLayout();
            setSpacing(true);
            setMargin(true);
            final Image image = new Image();
            image.setSource(new FileResource(reDocument.getDocument()));
            image.setHeight("250px");
            image.setWidth("250px");
            image.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    Image newImage = new Image();
                    newImage.setSource(image.getSource());
                    FullImageWindow fullImageWindow = new FullImageWindow(newImage);
                    UI.getCurrent().addWindow(fullImageWindow);
                }
            });
            box.addComponent(image);
            Button deleteButton = new Button("Удалить");
            deleteButton.setStyleName(Runo.BUTTON_LINK);
            deleteButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        reDocumentService.delete(reDocument);
                        removeComponent(box);
                        Notification.show("Документ удален успешно");
                    } catch (IOException e) {
                        Notification.show("Не удалось удалить документ");
                    }
                }
            });
            Label name = new Label("Название: " + reDocument.getFileName());
            box.addComponent(name);
            box.addComponent(deleteButton);
            addComponent(box);
            setComponentAlignment(box, Alignment.MIDDLE_LEFT);
        }

    }
}
