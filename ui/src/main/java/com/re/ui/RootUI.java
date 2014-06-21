package com.re.ui;

import com.re.dao.CommonHelper;
import com.re.service.RealEstateService;
import com.re.views.RealEstateView;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.vaadin.spring.VaadinUI;

@VaadinUI
@Widgetset("com.re.config.AppWidgetSet")
public class RootUI extends UI {
    private RealEstateView realEstateView;

    public RootUI(RealEstateView realEstateView) {
        this.realEstateView = realEstateView;
    }

    public RootUI() {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(realEstateView);
        CommonHelper.BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getPath();
    }
}
