package com.re.components.history;

import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;

public class REEditWindow extends Window{
    private RealEstateService realEstateService;
    private RealEstate entity;

    private TextField cadastralNumberField = new TextField("Кадастровый Номер");
    private TextField squareField = new TextField("Площадь");
    private RichTextArea areaDescriptionField = new RichTextArea("Описание");
    private TextField addressField = new TextField("Адрес");

    public REEditWindow(RealEstate entity, RealEstateService realEstateService) {
        this.entity = entity;
        this.realEstateService = realEstateService;
        center();
        setModal(true);
        initLayout();
        cadastralNumberField.setInputPrompt("номер..");
        squareField.setInputPrompt("площадь..");
        addressField.setInputPrompt("адрес..");
    }

    private void initLayout() {
        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setSpacing(true);
        final BeanFieldGroup binder = new BeanFieldGroup<RealEstate>(RealEstate.class);
        binder.setItemDataSource(entity);
        form.addComponent(cadastralNumberField);
        binder.bind(cadastralNumberField, "cadastralNumber");
        form.addComponent(squareField);
        binder.bind(squareField, "square");
        form.addComponent(areaDescriptionField);
        binder.bind(areaDescriptionField, "areaDescription");
        form.addComponent(addressField);
        binder.bind(addressField, "address");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {

                    if (binder.isValid()) {
                        binder.commit();
                        realEstateService.saveOrUdate(entity);
                        close();
                        Notification.show("Объект сохранен");
                    }
                } catch (FieldGroup.CommitException e) {
                    Notification.show("Ошибка!");
                    close();
                }
            }
        }));

        buttonLayout.addComponent(new Button("Отмена", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                binder.discard();
                close();
            }
        }));
        form.addComponent(buttonLayout);
        setContent(form);
    }
}
