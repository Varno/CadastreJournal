package com.re.components.history;

import com.re.entity.REHistory;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

public class HistoryTable extends Table {
    private BeanItemContainer<REHistory> datasource;

    public HistoryTable() {
        initTable();
    }

    private void initTable() {
        setContainerDataSource(getBeanItemContainer());
        setSizeFull();
        setVisibleColumns(getFields());
        setColumnHeaders(getHeaders());
        setPageLength(20);
    }

    @Override
    protected String formatPropertyValue(Object rowId,
                                         Object colId, Property property) {
        if (property.getType() == java.util.Calendar.class) {
            SimpleDateFormat df =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = (Calendar) property.getValue();
            return df.format(c.getTime());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    private String[] getHeaders() {
        List<String> list = new ArrayList<String>();
        list.add("ИД");
        list.add("Объект недвижимости");
        list.add("Описание");
        list.add("Кем изменен");
        list.add("Дата изменений");
        list.add("IP");
        return list.toArray(new String[list.size()]);
    }

    public BeanItemContainer<REHistory> getBeanItemContainer() {
        if (datasource == null) {
            datasource = new BeanItemContainer<REHistory>(REHistory.class);
        }
        return datasource;
    }

    protected String[] getFields() {
        List<String> list = new ArrayList<String>();
        list.add(REHistory.FIELD_ID);
        list.add(REHistory.FIELD_REAL_ESTATE_ID);
        list.add(REHistory.FIELD_DESCRIPTION);
        list.add(REHistory.FIELD_MODIFIED_BY);
        list.add(REHistory.FIELD_MODIFIED_DATE);
        list.add(REHistory.FIELD_MODIFIED_BY_IP);
        return list.toArray(new String[list.size()]);
    }

}
