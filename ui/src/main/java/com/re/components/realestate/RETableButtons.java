package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;

public class RETableButtons implements Table.ColumnGenerator {
    private RealEstateService realEstateService;
    private REHistoryService reHistoryService;


    public RETableButtons(RealEstateService realEstateService, REHistoryService reHistoryService) {
        this.realEstateService = realEstateService;
        this.reHistoryService = reHistoryService;
    }

    @Override
    public Object generateCell(final Table source, final Object itemId, Object columnId) {
        VerticalLayout vl = new VerticalLayout();
        Button edit = new Button("Редактировать");
        edit.setStyleName(Runo.BUTTON_LINK);
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Item item = source.getItem(itemId);
                BeanItem<RealEstate> beanItem = (BeanItem<RealEstate>) item;
                RealEstate realEstate = beanItem.getBean();
                REEditWindow reItemCard = new REEditWindow("Редактирование объекта недвижимости", realEstate, realEstateService);
                reItemCard.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent e) {
                        source.refreshRowCache();
                    }
                });
                UI.getCurrent().addWindow(reItemCard);
            }
        });
        Button show = new Button("Просмотр");
        show.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Item item = source.getItem(itemId);
                BeanItem<RealEstate> beanItem = (BeanItem<RealEstate>) item;
                RealEstate realEstate = beanItem.getBean();
                REItemCard reItemCard = new REItemCard("Карточка объекта",realEstate, reHistoryService);
                UI.getCurrent().addWindow(reItemCard);
            }
        });
        show.setStyleName(Runo.BUTTON_LINK);
        vl.setSpacing(true);
        vl.addComponent(show);
        vl.addComponent(edit);
        vl.setComponentAlignment(show, Alignment.MIDDLE_CENTER);
        vl.setComponentAlignment(edit, Alignment.MIDDLE_CENTER);
        return vl;
    }
}
