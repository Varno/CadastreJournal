package com.re.components.realestate;


import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class RETab extends VerticalLayout {
    private RETable realEstateTable;
    private ToolBar toolBar;
    private RealEstateService reService;

    public RETab(RETable realEstateTable, ToolBar toolBar, RealEstateService reService) {
        this.realEstateTable = realEstateTable;
        this.toolBar = toolBar;
        this.reService = reService;
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
