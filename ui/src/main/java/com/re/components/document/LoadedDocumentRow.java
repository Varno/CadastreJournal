package com.re.components.document;

import com.re.components.realestate.FullImageWindow;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;

public class LoadedDocumentRow extends HorizontalLayout {
    private String name;
    private FileResource documentResource;
    private Button showButton = new Button("Просмотр");
    private Button deleteButton = new Button("Удалить");

    public LoadedDocumentRow(String name, FileResource documentResource, boolean isAllowPreview) {
        this.name = name;
        this.documentResource = documentResource;
        setSpacing(true);
        setWidth("100%");
        setStyleName(Runo.LAYOUT_DARKER);
        Label nameLabel = new Label(name);
        nameLabel.setStyleName("h3");
        addComponent(nameLabel);
        setExpandRatio(nameLabel, 4);

        deleteButton.setStyleName(Runo.BUTTON_LINK);
        addComponent(deleteButton);
        setExpandRatio(deleteButton, 1);

        if (isAllowPreview) {
            showButton.setStyleName(Runo.BUTTON_LINK);
            addComponent(showButton);
            setExpandRatio(showButton, 1);
            initShowButtonListeners();
        }
    }

    private void initShowButtonListeners() {
        showButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(documentResource.getSourceFile()!= null && documentResource.getSourceFile().exists()){
                    Image image = new Image();
                    image.setSource(documentResource);
                    FullImageWindow fullImageWindow = new FullImageWindow(image);
                    UI.getCurrent().addWindow(fullImageWindow);
                } else {
                    Notification.show("Ошибка. Файл не найден");
                }
            }
        });
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
