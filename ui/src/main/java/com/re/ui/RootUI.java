package com.re.ui;

import com.re.components.RealEstateList;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.vaadin.spring.VaadinUI;

@VaadinUI
public class RootUI extends UI {
    private RealEstateList realEstateList;

    public RootUI(RealEstateList realEstateList) {
        this.realEstateList = realEstateList;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        realEstateList.init();
        setContent(realEstateList);
    }
}
