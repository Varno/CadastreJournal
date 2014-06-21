package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.vaadin.ui.*;


public class REItemCardTabInformation extends FormLayout {
    RealEstate realEstate;
    public REItemCardTabInformation(RealEstate realEstate) {
        this.realEstate = realEstate;
        initLayout();
    }

    private void initLayout() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        Label cadastralNumber = new Label(realEstate.getCadastralNumber());
        cadastralNumber.setCaption("Кадастровый №: ");
        addComponent(cadastralNumber);

        Label square = new Label(realEstate.getSquare().toString() + " м2");
        square.setCaption("Площадь: ");
        addComponent(square);

        Label areaDescription = new Label(realEstate.getAreaDescription());
        areaDescription.setCaption("Описание местности: ");
        addComponent(areaDescription);

        Label address = new Label(realEstate.getAddress());
        address.setCaption("Адрес: ");
        addComponent(address);

        Label destination = new Label(realEstate.getReDestination().getDescription());
        destination.setCaption("Назначение: ");
        addComponent(destination);

        Label usage = new Label(realEstate.getReUsage().getDescription());
        usage.setCaption("Разрешенное использование: ");
    }
}
