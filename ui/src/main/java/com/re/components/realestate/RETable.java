package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RETable extends Table {
    private BeanItemContainer<RealEstate> datasource;
    private REHistoryService reHistoryService;
    private RealEstateService realEstateService;
    private RETableButtons reTableActions;

    public RETable(RealEstateService realEstateService, REHistoryService reHistoryService,
                   Window.CloseListener editWindowCloseHandler) {
        this.reHistoryService = reHistoryService;
        this.realEstateService = realEstateService;
        this.reTableActions = new RETableButtons(realEstateService, reHistoryService, editWindowCloseHandler);
        initTable();
    }

    private void initTable() {
        setContainerDataSource(getBeanItemContainer());
        setWidth("80%");
        setHeight("100%");
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSortEnabled(false);
        setStyleName(Runo.TABLE_BORDERLESS);

        addGeneratedColumn("number", new RETableCadastreNumber());
        addGeneratedColumn("square", new RETableSquare());
        addGeneratedColumn("desc", new RETableDescription());
        addGeneratedColumn("actions", reTableActions);

        Object[] columns = new Object[]{"number", "square", "desc", "actions"};
        setVisibleColumns(columns);

        setColumnExpandRatio("number", 3);
        setColumnExpandRatio("square", 1);
        setColumnExpandRatio("desc", 14);
        setPageLength(10);
    }

     @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        if (property.getType() == java.util.Calendar.class) {
            SimpleDateFormat df =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = (Calendar) property.getValue();
            return df.format(c.getTime());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    public BeanItemContainer<RealEstate> getBeanItemContainer() {
        if (datasource == null) {
            datasource = new BeanItemContainer<RealEstate>(RealEstate.class);
        }
        return datasource;
    }
}
