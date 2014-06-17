package com.re.components;

import com.re.components.history.REEditWindow;
import com.re.components.history.REHistoryCard;
import com.re.entity.RealEstate;
import com.re.service.REHistoryService;
import com.re.service.RealEstateService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

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
            setSizeFull();
            setSelectable(true);
            setVisibleColumns(getFields());
            setColumnHeaders(getHeaders());
            setSortEnabled(false);
            initActions();
            setColumnExpandRatio(RealEstate.FIELD_ADDRESS, 7);
            setColumnExpandRatio(RealEstate.FIELD_ID, 1);
            setColumnExpandRatio(RealEstate.FIELD_CADASTRAL_NUMBER, 2);
            setColumnExpandRatio(RealEstate.FIELD_SQUARE, 1);
            setPageLength(25);
        }

    private void initActions() {
        final Action ACTION_SHOW = new Action("Просмотр");
        final Action ACTION_EDIT = new Action("Редактирование");
        final Action ACTION_HISTORY = new Action("История изменений");
        addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{
                        ACTION_SHOW,
                        ACTION_EDIT,
                        ACTION_HISTORY
                };
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (target != null && getItem(target) != null) {
                    final RealEstate realEstate = (RealEstate) target;
                    if (ACTION_SHOW == action) {
                        REItemCard reItemCard = new REItemCard("Карточка объекта",realEstate);
                        UI.getCurrent().addWindow(reItemCard);

                    } else if (ACTION_EDIT == action) {
                        REEditWindow reItemCard = new REEditWindow(realEstate, realEstateService);
                        reItemCard.addCloseListener(new Window.CloseListener() {
                            @Override
                            public void windowClose(Window.CloseEvent e) {


                            }
                        });
                        UI.getCurrent().addWindow(reItemCard);
                    } else if (ACTION_HISTORY == action) {
                        REHistoryCard reHistoryCard = new REHistoryCard(reHistoryService, realEstate.getId());
                        UI.getCurrent().addWindow(reHistoryCard);
                    }
                }
            }
        });
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
            list.add("Адрес");
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
            list.add(RealEstate.FIELD_ADDRESS);
            list.add(RealEstate.FIELD_SQUARE);
            return list.toArray(new String[list.size()]);
        }

}
