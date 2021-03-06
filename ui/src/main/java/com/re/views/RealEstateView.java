package com.re.views;

import com.re.components.history.HistoryTab;
import com.re.components.realestate.RETab;
import com.re.config.auth.LogoutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.navigator.VaadinView;


@VaadinView(name = "user")
public class RealEstateView extends VerticalLayout implements View {
    public static final String NAME = "user";
    private TabSheet tabSheet;
    private HistoryTab historyTab;
    private RETab reTab;

    public RealEstateView(HistoryTab historyTab, RETab reTab) {
        this.historyTab = historyTab;
        this.reTab = reTab;
    }

    private void initTabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            reTab.setSizeFull();
            tabSheet.addTab(reTab).setCaption("Объекты недвижимости");
            tabSheet.addTab(historyTab).setCaption("История изменений");
            TabSheet.Tab closeTab = tabSheet.addTab(new CssLayout());
            closeTab.setCaption("Выход");
            tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
                @Override
                public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
                    Component selectedTab = selectedTabChangeEvent.getTabSheet().getSelectedTab();
                    RETab reTab = (selectedTab instanceof RETab) ? (RETab) selectedTab : null;
                    HistoryTab historyTab = (selectedTab instanceof HistoryTab) ? (HistoryTab) selectedTab : null;
                    if (reTab != null) {
                        reTab.refresh();
                    }
                    else if (historyTab != null) {
                        historyTab.refresh();
                    }
                    else {
                        LogoutListener.logout();
                    }
                }
            });
        }
        addComponent(tabSheet);
    }

    private void initLayout() {
        setSizeFull();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        removeAllComponents();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            initLayout();
            initTabSheet();
        } else {
            Navigator navigator = UI.getCurrent().getNavigator();
            navigator.navigateTo("login");
        }
    }
}
