package com.re.components.realestate;


import com.re.entity.REDestination;
import com.re.entity.REDocument;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.re.util.MultiUpload;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;
import org.vaadin.peter.imagestrip.ImageStrip;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class REEditWindow extends Window {
    private RealEstateService realEstateService;
    private RealEstate entity;
    private TextField cadastralNumberField = new TextField("Кадастровый №");
    private TextField squareField = new TextField();
    private RichTextArea areaDescriptionField = new RichTextArea("Описание");
    private TextField addressField = new TextField("Адрес");
    private ComboBox destinationCombobox = new ComboBox("Назначение");
    private ComboBox usageCombobox = new ComboBox("Разрешенное использование");
    private MultiUpload multiUploader = new MultiUpload();

    public REEditWindow(RealEstate entity, RealEstateService realEstateService) {
        super(entity.getId() == null ? "Создать объект недвижимости" : "Изменить объект недвижимости");
        this.entity = entity;
        this.realEstateService = realEstateService;
        center();
        setModal(true);
        initLayout();
        multiUploader.setCaption("Документы: ");
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
                        try {
                            List<File> documentsList = multiUploader.getDocumentsList();
                            List<REDocument> reDocuments = new ArrayList<REDocument>();
                            String basePath = VaadinService.getCurrent().getBaseDirectory().getPath();
                            Path target = Paths.get(basePath + "/VAADIN/documents/");
                            if (!Files.exists(target)) {
                                Files.createDirectory(target);
                            }
                            for (File document : documentsList) {
                                Files.move(document.toPath(), target.resolve(document.getName()));
                                REDocument reDocument = new REDocument();
                                reDocument.setStoredPath(target.toAbsolutePath().toString());
                                reDocument.setFileName(document.getName());
                                reDocuments.add(reDocument);
                            }

                            entity.setReDocumentList(reDocuments);
                            realEstateService.saveOrUpdate(entity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

        form.addComponent(multiUploader);
        form.addComponent(buttonLayout);
        setContent(form);
    }

    public static Window Build(RealEstateService reService, RealEstate entity, CloseListener handler) {
        REEditWindow result = new REEditWindow(entity, reService);
        result.addCloseListener(handler);
        return result;
    }
}
