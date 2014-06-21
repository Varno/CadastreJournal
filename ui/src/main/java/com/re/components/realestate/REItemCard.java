package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.re.service.REDocumentService;
import com.re.service.REHistoryService;
import com.vaadin.ui.*;

public class REItemCard extends Window {
    private TabSheet tabSheet;
    private REItemCardTabInformation reItemCardTabInformation;
    private REItemCardTabHistory reItemCardTabHistory;
    private REItemCardTabDocuments reItemCardTabDocuments;
    final private REHistoryService reHistoryService;
    private REDocumentService reDocumentService;
    private RealEstate realEstate;

    public REItemCard(String caption, RealEstate realEstate, REHistoryService reHistoryService, REDocumentService reDocumentService) {
        super(caption);
        this.realEstate = realEstate;
        this.reHistoryService = reHistoryService;
        this.reDocumentService = reDocumentService;
        setModal(true);
        center();
        initLayout();
    }

    private void initLayout() {
        setWidth("70%");
        setHeight("80%");
        reItemCardTabInformation = new REItemCardTabInformation(realEstate);
        reItemCardTabHistory = new REItemCardTabHistory(reHistoryService, realEstate.getId());
        reItemCardTabDocuments = new REItemCardTabDocuments(realEstate, reDocumentService);
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.addTab(reItemCardTabInformation).setCaption("Информация");
            tabSheet.addTab(reItemCardTabHistory).setCaption("История");
            tabSheet.addTab(reItemCardTabDocuments).setCaption("Документы");
        }
        setContent(tabSheet);
    }
}
