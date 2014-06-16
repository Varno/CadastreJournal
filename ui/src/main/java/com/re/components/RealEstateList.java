package com.re.components;

import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.re.service.REHistoryServiceImpl;
import com.re.service.RealEstateService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RealEstateList extends VerticalLayout {
    private RealEstateService realEstateService;
    private REHistoryService reHistoryService;
    private Pagination pagination;
    private PagingComponent<RealEstate> pagingComponent;

    public RealEstateList(RealEstateService realEstateService, Pagination pagination,
                          REHistoryService reHistoryService) {
        this.realEstateService = realEstateService;
        this.pagination = pagination;
        this.reHistoryService = reHistoryService;
    }

    public void init() {
        final VerticalLayout itemsArea = new VerticalLayout();
        final int numberOfItemsPerPage = 20;
        final int numberOfItems = 2000;
        pagingComponent = new PagingComponent<RealEstate>(numberOfItemsPerPage, 4, new ArrayList<RealEstate>(numberOfItems),
                new LazyPagingComponentListener<RealEstate>(itemsArea) {

            @Override
            protected Collection<RealEstate> getItemsList(int startIndex, int endIndex) {
                List<RealEstate> realEstateList = realEstateService.getItemsFromRange(startIndex, numberOfItemsPerPage);
                return realEstateList;
            }
            @Override
            protected Component displayItem(int i, RealEstate realEstate) {
                return new REListItem(realEstate, reHistoryService);
            }
        });
        initPagination();

        Label cadastralNumber = new Label();
        cadastralNumber.setCaption("Кадастровый номер");
        cadastralNumber.setStyleName(Runo.LABEL_H2);

        Label address = new Label();
        address.setCaption("Адрес");
        address.setStyleName(Runo.LABEL_H2);
        Label sqare = new Label();
        sqare.setCaption("Площадь");
        sqare.setStyleName(Runo.LABEL_H2);
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidth("70%");
        headerLayout.addComponent(cadastralNumber);
        headerLayout.addComponent(address);
        headerLayout.addComponent(sqare);
        headerLayout.setExpandRatio(cadastralNumber, 5.5f);
        headerLayout.setExpandRatio(address, 3.0f);
        headerLayout.setExpandRatio(sqare, 1.0f);
        addComponent(headerLayout);
        addComponent(pagingComponent);
        addComponent(itemsArea);
        setComponentAlignment(itemsArea, Alignment.MIDDLE_CENTER);
        addComponent(pagination);


    }

    private void initPagination() {
 /*       // Allow to hide these buttons when the last page is selected
        ComponentsManager manager = pagingComponent.getComponentsManager();
        boolean isLastPage = !manager.isLastPage();
        pagination.getPreviousPage().setVisible(isLastPage);
        pagination.getNextPage().setVisible(isLastPage);

        // Allow to hide these buttons when the first page is selected
        boolean isFirst = !manager.isFirstPage();
        pagination.getPreviousPage().setVisible(isFirst);
        pagination.getNextPage().setVisible(isFirst);*/

        pagination.getNextPage().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                int currentPage = pagingComponent.getComponentsManager().getCurrentPage();
                pagingComponent.go(++currentPage);
            }
        });
        pagination.getPreviousPage().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                pagingComponent.go(pagingComponent.getComponentsManager().getPreviousPage());
            }
        });
    }
}

