package com.re.components;


import com.re.entity.RealEstate;
import com.vaadin.ui.*;

public class REItemCard extends Window {

    public REItemCard(String caption, RealEstate realEstate) {
        super(caption);
        center();
        setWidth("70%");
        setHeight("80%");
        VerticalLayout mainLayout = new VerticalLayout();
        Button showHistory = new Button("Историю редактирования");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);
        buttonLayout.addComponent(showHistory);

        FormLayout content = new FormLayout();
        content.setMargin(true);
        content.setSpacing(true);
        Label id = new Label(realEstate.getId().toString());
        id.setCaption("ID: ");
        content.addComponent(id);

        Label cadastralNumber = new Label(realEstate.getCadastralNumber());
        cadastralNumber.setCaption("Кадастровый номер: ");
        content.addComponent(cadastralNumber);

        Label square = new Label(realEstate.getSquare().toString());
        square.setCaption("Площадь: ");
        content.addComponent(square);

        Label areaDescription = new Label(realEstate.getAreaDescription());
        areaDescription.setCaption("Описание местности: ");
        content.addComponent(areaDescription);

        Label address = new Label(realEstate.getAddress());
        address.setCaption("Адрес: ");

        //mainLayout.addComponent(buttonLayout);
        mainLayout.addComponent(content);
        setContent(mainLayout);
    }
}
