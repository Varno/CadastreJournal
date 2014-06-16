package com.re.components.history;

import com.re.entity.REHistory;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class REHistoryListItem extends CustomComponent {
    public REHistoryListItem(REHistory reHistory) {
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label(reHistory.toString()));
        setCompositionRoot(hl);
    }
}
