package com.re.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Runo;


public class Pagination extends HorizontalLayout {
    private Button nextPage;
    private Button previousPage;
    public Pagination() {
        setSpacing(true);
        initPagination();
    }

    private void initPagination() {
        setHeight("10%");
        setWidth("100%");
        if(nextPage == null){
            nextPage  = new Button("Следующая >>");
            nextPage.setStyleName(Runo.BUTTON_LINK);
        }
        if(previousPage == null){
            previousPage = new Button("<< Предыдущая");
            previousPage.setStyleName(Runo.BUTTON_LINK);
        }
        Label separator = new Label(" | ");
        addComponent(previousPage);
        addComponent(separator);
        addComponent(nextPage);
        setComponentAlignment(previousPage, Alignment.MIDDLE_CENTER);
        setComponentAlignment(separator, Alignment.MIDDLE_CENTER);
        setComponentAlignment(nextPage, Alignment.MIDDLE_CENTER);
    }

    public Button getNextPage() {
        return nextPage;
    }

    public void setNextPage(Button nextPage) {
        this.nextPage = nextPage;
    }

    public Button getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(Button previousPage) {
        this.previousPage = previousPage;
    }
}
