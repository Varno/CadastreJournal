package com.re.components.history;


import com.re.entity.REHistory;
import com.re.service.REHistoryService;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ClientConnector;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class HistoryTab extends VerticalLayout {
    private HistoryTable historyTable;
    private REHistoryService reHistoryService;

    public HistoryTab(HistoryTable historyTable, REHistoryService reHistoryService) {
        this.historyTable = historyTable;
        this.reHistoryService = reHistoryService;
        initLayout();
    }

    private void initLayout() {
        addComponent(historyTable);
    }

    public void refresh() {
        historyTable.getBeanItemContainer().removeAllItems();
        int numberOfItems = reHistoryService.getNumberOfItems();
        List<REHistory> reHistoryList = reHistoryService.getHistoryItems(0, numberOfItems);
        historyTable.getBeanItemContainer().addAll(reHistoryList);
    }
}
