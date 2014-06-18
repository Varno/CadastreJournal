package com.re.components.realestate;


import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class RETab extends VerticalLayout {
    private RETable realEstateTable;
    private ToolBar toolBar;
    private final RealEstateService reService;

    public RETab(RETable realEstateTable, ToolBar toolBar, RealEstateService _reService) {
        this.reService = _reService;
        this.realEstateTable = realEstateTable;
        this.toolBar = toolBar;
        this.toolBar.getCreateREButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new REEditWindow("Создать объект недвижимости", new RealEstate(), reService));
            }
        });

        initLayout();
    }

    private void initLayout() {
        setSizeFull();
        addComponent(toolBar);
        addComponent(realEstateTable);
        setComponentAlignment(realEstateTable, Alignment.MIDDLE_CENTER);
        setComponentAlignment(toolBar, Alignment.MIDDLE_CENTER);
        toolBar.getSearchField().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String value = (String) event.getProperty().getValue();
                realEstateTable.getBeanItemContainer().removeAllItems();
                int numberOfItems = reService.getNumberOfItems(null);
                List<RealEstate> reHistoryList = reService.getItemsFromRange(null, value, 0, numberOfItems);
                realEstateTable.getBeanItemContainer().addAll(reHistoryList);
            }
        });
        refresh();
    }

    private void refresh() {
        realEstateTable.getBeanItemContainer().removeAllItems();
        int numberOfItems = reService.getNumberOfItems(null);
        List<RealEstate> reHistoryList = reService.getItemsFromRange(0, numberOfItems);
        realEstateTable.getBeanItemContainer().addAll(reHistoryList);
    }
}
