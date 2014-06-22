package com.re.components.realestate;


import com.vaadin.server.Resource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Window;

public class FullImageWindow extends Window {
    Image image;

    public FullImageWindow(Image image) {
        this.image = image;
        image.setSizeFull();
        setWidth("90%");
        setHeight("90%");
        setModal(true);
        center();
        CssLayout mainLayout = new CssLayout();
        mainLayout.addComponent(image);
        setContent(mainLayout);
    }
}
