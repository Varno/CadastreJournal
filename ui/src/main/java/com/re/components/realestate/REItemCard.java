package com.re.components.realestate;


import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.vaadin.ui.*;

public class REItemCard extends Window {
    private TabSheet tabSheet;
    private REItemCardTabInformation reItemCardTabInformation;
    private REItemCardTabHistory reItemCardTabHistory;
    final private REHistoryService reHistoryService;
    private RealEstate realEstate;

    public REItemCard(String caption, RealEstate realEstate, REHistoryService reHistoryService) {
        super(caption);
        this.realEstate = realEstate;
        this.reHistoryService = reHistoryService;
        center();
        initLayout();
    }

    private void initLayout() {
        setWidth("70%");
        setHeight("80%");
        reItemCardTabInformation = new REItemCardTabInformation(realEstate);
        reItemCardTabHistory = new REItemCardTabHistory(reHistoryService, realEstate.getId());
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.addTab(reItemCardTabInformation).setCaption("Информация");
            tabSheet.addTab(reItemCardTabHistory).setCaption("История");
        }
        setContent(tabSheet);
    }
}
