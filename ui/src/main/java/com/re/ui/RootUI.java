package com.re.ui;

import com.re.views.RealEstateView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.vaadin.spring.VaadinUI;

@VaadinUI
public class RootUI extends UI {
    private RealEstateView realEstateView;

    public RootUI(RealEstateView realEstateView) {
        this.realEstateView = realEstateView;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(realEstateView);
    }
}
