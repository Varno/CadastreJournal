package com.re.components.realestate;
import com.re.entity.RealEstate;
import com.vaadin.ui.*;


public class REItemCardTabInformation extends VerticalLayout {
    RealEstate realEstate;
    public REItemCardTabInformation(RealEstate realEstate) {
        this.realEstate = realEstate;
        initLayout();
    }

    private void initLayout() {
        setSizeFull();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);

        FormLayout content = new FormLayout();
        content.setMargin(true);
        content.setSpacing(true);
        Label cadastralNumber = new Label(realEstate.getCadastralNumber());
        cadastralNumber.setCaption("Кадастровый №: ");
        content.addComponent(cadastralNumber);

        Label square = new Label(realEstate.getSquare().toString() + " м2");
        square.setCaption("Площадь: ");
        content.addComponent(square);

        Label areaDescription = new Label(realEstate.getAreaDescription());
        areaDescription.setCaption("Описание местности: ");
        content.addComponent(areaDescription);

        Label address = new Label(realEstate.getAddress());
        address.setCaption("Адрес: ");
        content.addComponent(address);

        Label destination = new Label(realEstate.getReDestination().getDescription());
        destination.setCaption("Назначение: ");
        content.addComponent(destination);

        Label usage = new Label(realEstate.getReUsage().getDescription());
        usage.setCaption("Разрешенное использование: ");
        content.addComponent(usage);
        addComponent(content);
    }
}
