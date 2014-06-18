package com.re.views;

import com.re.components.RETable;
import com.re.components.ToolBar;
import com.re.components.history.REEditWindow;
import com.re.entity.REHistory;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;

public class RealEstateView extends VerticalLayout {

    private RETable realEstateTable;
    private ToolBar toolBar;
    private RealEstateService reService;

    public RealEstateView(RETable realEstateTable, ToolBar toolBar, RealEstateService reService) {
        this.realEstateTable = realEstateTable;
        realEstateTable.getBeanItemContainer().addItemSetChangeListener(new Container.ItemSetChangeListener() {
            @Override
            public void containerItemSetChange(com.vaadin.data.Container.ItemSetChangeEvent itemSetChangeEvent) {

            }
        });
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
        toolBar.getCreateButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final RealEstate entity = new RealEstate();
                Window editWindow = new REEditWindow(new RealEstate(), reService);
                editWindow.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent closeEvent) {
                        realEstateTable.getBeanItemContainer().addBean(entity);
                    }
                });
                UI.getCurrent().addWindow(editWindow);
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
