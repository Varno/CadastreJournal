package com.re.views;

import com.re.components.history.HistoryTab;
import com.re.components.realestate.RETab;
import com.re.components.realestate.RETable;
import com.re.components.realestate.ToolBar;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class RealEstateView extends VerticalLayout {
    private TabSheet tabSheet;
    private HistoryTab historyTab;
    private RETab reTab;

    public RealEstateView(HistoryTab historyTab, RETab reTab) {
        this.historyTab = historyTab;
        this.reTab = reTab;
        initLayout();
        initTabSheet();
    }

    private void initTabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.addTab(reTab).setCaption("Объекты недвижимости");
            tabSheet.addTab(historyTab).setCaption("История изменений");
        }
        addComponent(tabSheet);
    }

    private void initLayout() {
        setSizeFull();
    }


}
