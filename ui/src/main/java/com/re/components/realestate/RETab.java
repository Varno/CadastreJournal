package com.re.components.realestate;


import com.re.entity.RealEstate;
import com.re.service.REDocumentService;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

public class RETab extends VerticalLayout {
    private RETable realEstateTable;
    private ToolBar toolBar;
    private final RealEstateService reService;
    private REDocumentService reDocumentService;
    private Window.CloseListener editWindowCloseHandler = new Window.CloseListener() {
        @Override
        public void windowClose(Window.CloseEvent closeEvent) {
            refresh();
        }
    };

    public RETab(ToolBar toolBar, RealEstateService _reService, REHistoryService _histService, final REDocumentService reDocumentService) {
        this.reService = _reService;
        this.reDocumentService = reDocumentService;
        this.realEstateTable = new RETable(reService, _histService, editWindowCloseHandler, reDocumentService);
        this.toolBar = toolBar;
        this.toolBar.getCreateREButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window editWindow = REEditWindow.Build(reService, new RealEstate(), reDocumentService, editWindowCloseHandler);
                UI.getCurrent().addWindow(editWindow);
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
                RETable.setSearchQuery(value);
                realEstateTable.getLazyLoadContainer().refresh();
                RETable.setSearchQuery(null);
            }
        });
    }

    public void refresh() {
        realEstateTable.getLazyLoadContainer().refresh();
    }
}
