package com.re.components;

import com.re.components.history.REEditWindow;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.*;


public class ToolBar extends HorizontalLayout {
    private RealEstateService reService;
    private Button createREButton;
    private TextField searchField;

    public ToolBar(RealEstateService reService) {
        this.reService = reService;
        initButton();
        initSearch();
        setSpacing(true);
        setMargin(true);
        setWidth("50%");
        addComponent(createREButton);
        addComponent(searchField);
        setComponentAlignment(createREButton, Alignment.BOTTOM_LEFT);
        setComponentAlignment(searchField, Alignment.BOTTOM_LEFT);
    }

    private void initSearch() {
        if(searchField == null){
            searchField = new TextField();
            searchField.setValue("Поиск..");
            searchField.setColumns(30);
            searchField.setImmediate(true);
        }
    }

    private void initButton() {
        if(createREButton == null){
            createREButton = new Button("Добавить");
            createREButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    UI.getCurrent().addWindow(new REEditWindow(new RealEstate(), reService));
                }
            });
        }
    }

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }
}
