package com.re.components.realestate;

import com.re.entity.REDestination;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;
import com.re.service.REDocumentService;
import com.re.service.RealEstateService;
import com.re.components.util.MultiUpload;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import java.io.IOException;
import java.util.List;

public class REEditWindow extends Window {
    private static final String SQUARE_VALID_ERR_MSG =
            "Не допустимое значение. Допускается только положительное целое или вещественное число";
    private RealEstateService realEstateService;
    private REDocumentService reDocumentService;
    private RealEstate entity;
    private TextField cadastralNumberField = new TextField("Кадастровый №");
    private TextField squareField = new TextField();
    private TextArea areaDescriptionField = new TextArea("Описание");
    private TextField addressField = new TextField("Адрес");
    private ComboBox destinationCombobox = new ComboBox("Назначение");
    private ComboBox usageCombobox = new ComboBox("Разрешенное использование");
    private MultiUpload multiUploader;

    public REEditWindow(RealEstate entity, RealEstateService realEstateService, REDocumentService reDocumentService) {
        super(entity.getId() == null ? "Создать объект недвижимости" : "Изменить объект недвижимости");
        this.entity = entity;
        this.realEstateService = realEstateService;
        multiUploader = new MultiUpload(reDocumentService);
        center();
        setModal(true);
        initLayout();
        initFormField();
        initDocumentsInForm(entity);
    }

    private void initDocumentsInForm(RealEstate entity) {
        if(entity.getId() != null && entity.getReDocumentList() != null && !entity.getReDocumentList().isEmpty()){
            multiUploader.addREDocumentsToForm(entity.getReDocumentList());
        }

    }

    private void initFormField() {
        multiUploader.setCaption("Документы: ");
        cadastralNumberField.setInputPrompt("номер..");
        cadastralNumberField.addValidator(new StringLengthValidator("Кадастровый номер должен быть от 4 до 20 символов",
                4, 20, false));

        squareField.setInputPrompt("площадь..");
        squareField.addValidator(new DoubleRangeValidator(
                SQUARE_VALID_ERR_MSG,
                0D, Double.MAX_VALUE));
        squareField.setConversionError(SQUARE_VALID_ERR_MSG);
        squareField.setValidationVisible(false);
        squareField.setImmediate(false);

        areaDescriptionField.setWidth(400, Unit.PIXELS);
        areaDescriptionField.setInputPrompt("описание..");
        areaDescriptionField.addValidator(new StringLengthValidator("Поле обязательно для заполнения", 1, 4000, false));

        addressField.setInputPrompt("адрес..");
        addressField.addValidator(new StringLengthValidator("Поле обязательно для заполнения", 1, 1024, false));
        destinationCombobox.addValidator(new NullValidator("Поле обязательно для заполнения", false));
        usageCombobox.addValidator(new NullValidator("Поле обязательно для заполнения", false));
    }


    private void initLayout() {
        FormLayout form = new FormLayout() {
            @Override
            public void addComponent(com.vaadin.ui.Component c) {
                super.addComponent(c);
                if (c instanceof AbstractField) {
                    AbstractField af = (AbstractField) c;
                    af.setValidationVisible(false);
                    af.setImmediate(false);
                }
            }
        };
        final BeanFieldGroup binder = new BeanFieldGroup<RealEstate>(RealEstate.class) {
            @Override
            public boolean isValid() {
                boolean result = super.isValid();
                for (Object property : getBoundPropertyIds()) {
                    Field field = getField(property);
                    if (field instanceof AbstractField) {
                        AbstractField af = (AbstractField) field;
                        af.setValidationVisible(!result);
                    }
                }
                squareField.setValidationVisible(!result);
                return result;
            }
        };
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
                            entity.setReDocumentList(multiUploader.getReDocumentsList());
                            realEstateService.saveOrUpdate(entity);
                        }
                        catch (org.springframework.dao.DuplicateKeyException e) {
                            throw new FieldGroup.CommitException(
                                    String.format("Объект недвижимости с кадастровым номером %s уже существует. Измените кадастровый номер и повторите сохранение", entity.getCadastralNumber()));
                        }
                        catch (org.springframework.dao.DataAccessException e) {
                            throw new FieldGroup.CommitException(e.getMessage(), e.getCause());
                        } catch (IOException e) {
                            throw new FieldGroup.CommitException("Не удалось сохранить документы");
                        }

                        close();
                        Notification.show("Объект сохранен");
                    } else
                        Notification.show("Невозможно сохранить некорректные изменения, нужно исправить некоректные значения в полях ввода и повторить сохранение еще раз.");
                } catch (FieldGroup.CommitException e) {
                    Notification.show("Ошибка сохранения. Подробнее: " + e.getMessage());
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

    public static Window Build(RealEstateService reService, RealEstate entity, REDocumentService reDocumentService, CloseListener handler) {
        if (entity.getId() != null)
            entity = reService.getItem(entity.getId());
        REEditWindow result = new REEditWindow(entity, reService, reDocumentService);
        result.addCloseListener(handler);
        return result;
    }
}
