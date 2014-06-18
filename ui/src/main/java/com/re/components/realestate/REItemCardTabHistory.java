package com.re.components.realestate;


import com.re.components.history.HistoryTable;
import com.re.entity.REHistory;
import com.re.service.REHistoryService;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class REItemCardTabHistory extends VerticalLayout {
    final private REHistoryService reHistoryService;
    private Long facilityId;
    private HistoryTable historyTable;
    public REItemCardTabHistory(REHistoryService reHistoryService, Long facilityId) {

        this.reHistoryService = reHistoryService;
        this.facilityId = facilityId;
        initLayout();
    }

    private void initLayout() {
        setSizeFull();
        historyTable = new HistoryTable();
        addComponent(historyTable);
        refresh();
    }

    private void refresh() {
        historyTable.getBeanItemContainer().removeAllItems();
        int numberOfItems = reHistoryService.getNumberOfItems(facilityId);
        List<REHistory> reHistoryList = reHistoryService.getHistoryItems(facilityId, 0, numberOfItems);
        historyTable.getBeanItemContainer().addAll(reHistoryList);
    }
}
