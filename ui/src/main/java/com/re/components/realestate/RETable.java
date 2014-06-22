package com.re.components.realestate;

import com.re.service.REDocumentService;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.re.util.REBeanQuery;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;
import org.vaadin.addons.lazyquerycontainer.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class RETable extends Table {
    public static String searchQuery = null;
    private RealEstateService realEstateService;
    private RETableButtons reTableActions;
    private LazyQueryContainer lazyLoadContainer;


    public RETable(RealEstateService realEstateService, REHistoryService reHistoryService,
                   Window.CloseListener editWindowCloseHandler, REDocumentService reDocumentService) {
        this.realEstateService = realEstateService;
        this.reTableActions = new RETableButtons(realEstateService, reHistoryService, editWindowCloseHandler, reDocumentService);
        initTable();
    }

    private void initTable() {
        setContainerDataSource(getLazyLoadContainer());
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
        setPageLength(7);
        setCacheRate(1);
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

    public LazyQueryContainer getLazyLoadContainer() {
        if(lazyLoadContainer == null){
            BeanQueryFactory<REBeanQuery> beanQueryFactory = new BeanQueryFactory<REBeanQuery>(REBeanQuery.class);
            Map<String, Object> queryConfiguration = new HashMap<String, Object>();
            queryConfiguration.put("realEstateService", realEstateService);
            beanQueryFactory.setQueryConfiguration(queryConfiguration);
            QueryDefinition queryDefinition = new LazyQueryDefinition(false, 50, null);
            queryDefinition.setDefaultSortPropertyIds(null);
            LazyQueryView lazyQueryView = new LazyQueryView(queryDefinition, beanQueryFactory);
            lazyLoadContainer = new LazyQueryContainer(lazyQueryView);
        }

        return lazyLoadContainer;
    }

    public static String getSearchQuery() {
        return searchQuery;
    }

    public static void setSearchQuery(String searchQuery) {
        RETable.searchQuery = searchQuery;
    }
}
