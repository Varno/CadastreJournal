package com.re.views;

import com.re.components.history.HistoryTab;
import com.re.components.realestate.RETab;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

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
            tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
                @Override
                public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
                    Component selectedTab = selectedTabChangeEvent.getTabSheet().getSelectedTab();
                    HistoryTab historyTab = (selectedTab instanceof HistoryTab) ? (HistoryTab) selectedTab : null;
                    if (historyTab != null) {
                        historyTab.refresh();
                    }
                }
            });
        }
        addComponent(tabSheet);
    }

    private void initLayout() {
        setSizeFull();
    }


}
