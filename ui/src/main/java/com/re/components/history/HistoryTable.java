package com.re.components.history;

import com.re.components.util.CommonSettings;
import com.re.entity.REHistory;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;

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
        setStyleName(Runo.TABLE_BORDERLESS);
        setVisibleColumns(getFields());
        setSortEnabled(false);
        setColumnHeaders(getHeaders());
        setPageLength(20);
    }

    @Override
    protected String formatPropertyValue(Object rowId,
                                         Object colId, Property property) {
        if (property.getType() == java.util.Calendar.class) {
            Calendar c = (Calendar) property.getValue();
            return CommonSettings.DATE_FORMAT.format(c.getTime());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

    private String[] getHeaders() {
        List<String> list = new ArrayList<String>();
        list.add("ID");
        list.add("Действие");
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
        list.add(REHistory.FIELD_ACTION);
        list.add(REHistory.FIELD_DESCRIPTION);
        list.add(REHistory.FIELD_MODIFIED_BY);
        list.add(REHistory.FIELD_MODIFIED_DATE);
        list.add(REHistory.FIELD_MODIFIED_BY_IP);
        return list.toArray(new String[list.size()]);
    }

}
