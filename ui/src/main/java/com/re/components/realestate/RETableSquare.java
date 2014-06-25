package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RETableSquare implements Table.ColumnGenerator {
    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        CssLayout vl = new CssLayout();
        Item item = source.getItem(itemId);
        BeanItem<RealEstate> beanItem = (BeanItem<RealEstate>) item;
        RealEstate realEstate = beanItem.getBean();
        Label square = new Label(realEstate.getSquare().toString() + " м2");
        vl.addComponent(square);
        return vl;
    }
}
