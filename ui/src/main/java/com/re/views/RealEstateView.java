package com.re.views;

import com.re.components.RETable;
import com.re.components.ToolBar;
import com.re.entity.REHistory;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class RealEstateView extends VerticalLayout {

    private RETable realEstateTable;
    private ToolBar toolBar;
    private RealEstateService reService;

    public RealEstateView(RETable realEstateTable, ToolBar toolBar, RealEstateService reService) {
        this.realEstateTable = realEstateTable;
        this.toolBar = toolBar;
        this.reService = reService;
        initLayout();
        refresh();
    }

    private void initLayout() {
        addComponent(toolBar);
        addComponent(realEstateTable);
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
    }

    private void refresh(){
        realEstateTable.getBeanItemContainer().removeAllItems();
        int numberOfItems = reService.getNumberOfItems(null);
        List<RealEstate> reHistoryList = reService.getItemsFromRange(0, numberOfItems);
        realEstateTable.getBeanItemContainer().addAll(reHistoryList);
    }
}
