package com.re.components.history;


import com.re.entity.REHistory;
import com.re.service.REHistoryService;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;


public class REHistoryCard extends Window {

    final private REHistoryService reHistoryService;
    private Long facilityId;
    private HistoryTable historyTable;

    public REHistoryCard(REHistoryService reHistoryService, Long facilityId) {
        super("История изменений объекта недвижимости");
        this.reHistoryService = reHistoryService;
        this.facilityId = facilityId;
        initLayout();
    }

    private void initLayout() {
        center();
        setWidth("70%");
        setHeight("80%");
        setResizable(false);
        VerticalLayout mainLayout = new VerticalLayout();
        historyTable = new HistoryTable();
        mainLayout.addComponent(historyTable);
        setContent(mainLayout);
        refresh();
    }

    private void refresh() {
        historyTable.getBeanItemContainer().removeAllItems();
        int numberOfItems = reHistoryService.getNumberOfItems(facilityId);
        List<REHistory> reHistoryList = reHistoryService.getHistoryItems(facilityId, 0, numberOfItems);
        historyTable.getBeanItemContainer().addAll(reHistoryList);
    }
}
