package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RETableDescription implements Table.ColumnGenerator {
    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        Item item = source.getItem(itemId);
        BeanItem<RealEstate> beanItem = (BeanItem<RealEstate>) item;
        RealEstate realEstate = beanItem.getBean();
        Label desc = new Label(realEstate.getAreaDescription());
        Label address = new Label("Адрес: " + realEstate.getAddress());
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        Label destination = new Label("Назначение: " + realEstate.getReDestination().getDescription());
        if(realEstate.getReUsage() != null){
            Label usage = new Label("Разрешенное использование: " + realEstate.getReUsage().getDescription());
            hl.addComponent(usage);
        }

        hl.addComponent(destination);
        vl.addComponent(desc);
        vl.addComponent(address);
        vl.addComponent(hl);

        return vl;
    }
}
