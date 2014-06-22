package com.re.components.realestate;

import com.re.components.util.CommonSettings;
import com.re.entity.REDestination;
import com.re.entity.RealEstate;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RETableDescription implements Table.ColumnGenerator {
    public static final String FACILITY_ID = "FACILITY_ID";

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        Item item = source.getItem(itemId);
        BeanItem<RealEstate> beanItem = (BeanItem<RealEstate>) item;
        RealEstate realEstate = beanItem.getBean();
        Label desc = new Label(realEstate.getAreaDescription());
        Label address = new Label("Адрес: " + realEstate.getAddress());
        vl.addComponent(desc);
        vl.addComponent(address);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);

        if (realEstate.getReDestination() != null) {
            Label destination = new Label("Назначение: " + realEstate.getReDestination().getDescription());
            hl.addComponent(destination);
        }
        if(realEstate.getReUsage() != null){
            Label usage = new Label("Разрешенное использование: " + realEstate.getReUsage().getDescription());
            hl.addComponent(usage);
        }
        if (realEstate.getModifiedDate() != null) {
            Label modifiedDate = new Label("Время последнего изменения: "
                    + CommonSettings.DATE_FORMAT.format(realEstate.getModifiedDate().getTime()));
            hl.addComponent(modifiedDate);
        }
        vl.addComponent(hl);

        return vl;
    }
}
