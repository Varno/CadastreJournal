package com.re.components.realestate;

import com.re.config.auth.LogoutListener;
import com.vaadin.ui.*;


public class ToolBar extends HorizontalLayout {
    private Button createREButton;
    private TextField searchField;

    public ToolBar() {
        initButton();
        initSearch();

        setSpacing(true);
        setMargin(true);
        setWidth("50%");
        addComponent(createREButton);
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.addComponent(searchField);
        addComponent(hl);
        setComponentAlignment(createREButton, Alignment.BOTTOM_LEFT);
        setComponentAlignment(hl, Alignment.BOTTOM_LEFT);
    }

    private void initSearch() {
        if(searchField == null){
            searchField = new TextField("Поиск");
            searchField.setInputPrompt("...");
            searchField.setColumns(30);
            searchField.setImmediate(true);
        }
    }

    private void initButton() {
        if(createREButton == null){
            createREButton = new Button("Добавить");
        }
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getCreateREButton() {
        return this.createREButton;
    }
}
