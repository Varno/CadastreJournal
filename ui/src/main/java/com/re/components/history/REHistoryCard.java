package com.re.components.history;


import com.re.entity.REHistory;
import com.re.service.REHistoryService;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.vaadin.LazyList;

import java.util.ArrayList;
import java.util.List;

public class REHistoryCard extends Window {

    final private REHistoryService reHistoryService;
    private Long facilityId;

    public REHistoryCard(REHistoryService reHistoryService, Long facilityId) {
        super("История изменений объекта недвижимости");
        this.reHistoryService = reHistoryService;
        this.facilityId = facilityId;
        center();
        setWidth("70%");
        setHeight("80%");
        VerticalLayout mainLayout = new VerticalLayout();

        final LazyList lazylist = new LazyList(initLazyFetcher());
        lazylist.setSizeFull();
        mainLayout.addComponent(lazylist);
        setContent(mainLayout);
    }

    private LazyList.LazyItemFetcher initLazyFetcher(){
        LazyList.LazyItemFetcher itemFetcher = new LazyList.LazyItemFetcher() {
            private int skip = 0;
            @Override
            public List getMoreItems() {
                List<REHistoryListItem> reHistoryListItems = new ArrayList<REHistoryListItem>();
                List<REHistory> reHistoryList = reHistoryService.getHistoryItems(facilityId, skip, 20);
                for(REHistory reh: reHistoryList){
                    REHistoryListItem reHistoryListItem = new REHistoryListItem(reh);
                    reHistoryListItems.add(reHistoryListItem);
                }
                skip += 20;
                return reHistoryListItems;
            }
        };
        return itemFetcher;
    }
}
