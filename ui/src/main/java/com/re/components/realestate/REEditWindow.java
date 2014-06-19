package com.re.components.realestate;

import com.re.components.util.Uploader;
import com.re.entity.REDestination;
import com.re.entity.REDocument;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class REEditWindow extends Window{
    private RealEstateService realEstateService;
    private RealEstate entity;

    private TextField cadastralNumberField = new TextField("Кадастровый №");
    private TextField squareField = new TextField();
    private RichTextArea areaDescriptionField = new RichTextArea("Описание");
    private TextField addressField = new TextField("Адрес");
    private ComboBox destinationCombobox = new ComboBox("Назначение");
    private ComboBox usageCombobox = new ComboBox("Разрешенное использование");
    private Uploader uploader = new Uploader();


    public REEditWindow(RealEstate entity, RealEstateService realEstateService) {
        super(entity.getId() == null ? "Создать объект недвижимости" : "Изменить объект недвижимости");
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
        cadastralNumberField.setReadOnly(entity.getId() != null);

        HorizontalLayout squareLayout = new HorizontalLayout();
        squareLayout.setSpacing(true);
        squareLayout.setCaption("Площадь");
        squareLayout.addComponent(squareField);
        squareLayout.addComponent(new Label(" м2"));
        form.addComponent(squareLayout);
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
                        if(uploader.getImage() != null){
                            REDocument reDocument = new REDocument();
                            reDocument.setStoredPath(uploader.getFilename());
                            entity.setReDocumentList(new ArrayList<REDocument>());
                            entity.getReDocumentList().add(reDocument);
                            String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
                            Path target = Paths.get(basepath + File.separator + "WEB-INF" + File.separator + "images" + File.separator + uploader.getFilename());

                            try {
                                Path file = Files.createFile(target);
                                Files.write(file, uploader.getOs().toByteArray(), StandardOpenOption.WRITE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        realEstateService.saveOrUpdate(entity);
                        close();
                    }
                    Notification.show("Объект сохранен");
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

        form.addComponent(uploader);
        form.addComponent(buttonLayout);

        setContent(form);
    }

    public static Window Build(RealEstateService reService, RealEstate entity, CloseListener handler){
        REEditWindow result = new REEditWindow(entity, reService);
        result.addCloseListener(handler);
        return  result;
    }
}
