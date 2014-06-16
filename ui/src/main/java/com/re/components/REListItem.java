package com.re.components;

import com.re.components.history.REHistoryCard;
import com.re.entity.*;
import com.re.service.REHistoryService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Runo;

public class REListItem extends HorizontalLayout{
    private Button editButton;
    private Button showButton;
    private Button historyButton;
    private RealEstate item;
    private REHistoryService reHistoryService;

    public REListItem(RealEstate realEstateItem, REHistoryService reHistoryService) {
        this.item = realEstateItem;
        this.reHistoryService = reHistoryService;
        setHeight("5%");
        setWidth("100%");
        setMargin(true);
        setSpacing(true);
        Label cadastralNumber = new Label(item.getCadastralNumber());
        Label address = new Label(item.getAddress());
        Label sqare = new Label(item.getSquare().toString());
        initButtons();
        addComponent(cadastralNumber);
        addComponent(address);
        addComponent(sqare);
        addComponent(showButton);
        addComponent(editButton);
        addComponent(historyButton);
        setExpandRatio(cadastralNumber, 1.0f);
        setExpandRatio(address, 3.0f);
        setExpandRatio(sqare, 1.0f);
    }

    private void initButtons() {
        editButton = new Button("Редактировать");
        showButton = new Button("Показать");
        historyButton = new Button("История");
        editButton.setStyleName(Runo.BUTTON_SMALL);
        showButton.setStyleName(Runo.BUTTON_SMALL);
        historyButton.setStyleName(Runo.BUTTON_SMALL);

        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                REItemCard card = new REItemCard("Карточка объекта", item);
                UI.getCurrent().addWindow(card);

            }
        });

        showButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                REItemCard card = new REItemCard("Карточка объекта", item);
                UI.getCurrent().addWindow(card);
            }
        });

        historyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                REHistoryCard reHistoryCard = new REHistoryCard(reHistoryService, item.getId());
                UI.getCurrent().addWindow(reHistoryCard);
            }
        });
    }
}
