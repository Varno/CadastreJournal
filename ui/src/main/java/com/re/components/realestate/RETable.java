package com.re.components.realestate;

import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RETable extends Table {
        private BeanItemContainer<RealEstate> datasource;
        private REHistoryService reHistoryService;
        private RealEstateService realEstateService;

        public RETable(RealEstateService realEstateService, REHistoryService reHistoryService) {
            this.reHistoryService = reHistoryService;
            this.realEstateService = realEstateService;
            initTable();
        }

        private void initTable() {
            setContainerDataSource(getBeanItemContainer());
            setWidth("80%");
            setHeight("100%");
            setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
            setStyleName(Runo.TABLE_BORDERLESS);
            setVisibleColumns(getFields());
            setColumnHeaders(getHeaders());
            setSortEnabled(false);
            addGeneratedColumn("Описание", new RETableDescription());
            addGeneratedColumn("Выбор", new RETableButtons(realEstateService, reHistoryService));

            setColumnExpandRatio(RealEstate.FIELD_CADASTRAL_NUMBER, 2);
            setColumnExpandRatio(RealEstate.FIELD_SQUARE, 1);
            setColumnExpandRatio("Описание", 14);
            setPageLength(25);
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
            list.add("Кадастровый номер");
            list.add("Площадь");
            return list.toArray(new String[list.size()]);
        }

        public BeanItemContainer<RealEstate> getBeanItemContainer() {
            if (datasource == null) {
                datasource = new BeanItemContainer<RealEstate>(RealEstate.class);
            }
            return datasource;
        }

        protected String[] getFields() {
            List<String> list = new ArrayList<String>();
            list.add(RealEstate.FIELD_CADASTRAL_NUMBER);
            list.add(RealEstate.FIELD_SQUARE);
            return list.toArray(new String[list.size()]);
        }

    public RealEstateService getRealEstateService() {
        return realEstateService;
    }
}
