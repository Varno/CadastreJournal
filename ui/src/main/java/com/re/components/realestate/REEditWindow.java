package com.re.components.realestate;


import com.re.dao.destination.DestinationDao;
import com.re.entity.REDestination;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.util.List;

public class REEditWindow extends Window{
    private RealEstateService realEstateService;
    private RealEstate entity;

    @PropertyId("cadastralNumber")
    private TextField cadastralNumberField = new TextField("Кадастровый Номер");
    @PropertyId("square")
    private TextField squareField = new TextField("Площадь");
    @PropertyId("areaDescription")
    private RichTextArea areaDescriptionField = new RichTextArea("Описание");
    @PropertyId("address")
    private TextField addressField = new TextField("Адрес");
    @PropertyId("reDestination.")
    private ComboBox destinationCombobox = new ComboBox("Назначение");
    @PropertyId("reUsage")
    private ComboBox usageCombobox = new ComboBox("Разрешенное использование");

    public REEditWindow(String caption, RealEstate entity, RealEstateService realEstateService) {
        super(caption);
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
        addressField.setWidth("100%");
        destinationCombobox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        destinationCombobox.setItemCaptionPropertyId("description");
        destinationCombobox.setNullSelectionAllowed(false);
        destinationCombobox.setWidth("50%");
        List<REDestination> reDestinationList = realEstateService.findAllREDestinations();
        BeanItemContainer<REDestination> reDestinationsContainer = new BeanItemContainer<REDestination>(REDestination.class);
        reDestinationsContainer.removeAllItems();
        reDestinationsContainer.addAll(reDestinationList);
        destinationCombobox.setContainerDataSource(reDestinationsContainer);

        usageCombobox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        usageCombobox.setItemCaptionPropertyId("description");
        usageCombobox.setNullSelectionAllowed(false);
        usageCombobox.setWidth("50%");
        List<REUsage> reUsagesList = realEstateService.findAllREUsages();
        BeanItemContainer<REUsage> reUsagesContainer = new BeanItemContainer<REUsage>(REUsage.class);
        reUsagesContainer.removeAllItems();
        reUsagesContainer.addAll(reUsagesList);
        usageCombobox.setContainerDataSource(reUsagesContainer);
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
        form.addComponent(destinationCombobox);
        binder.bind(destinationCombobox, "reDestination");
        form.addComponent(usageCombobox);
        binder.bind(usageCombobox, "reUsage");
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
                    }
                    Notification.show("Объект добавлен");
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
