package com.re.ui;

import com.re.dao.CommonHelper;
import com.re.views.LoginView;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.SpringViewProvider;

@VaadinUI
@Widgetset("com.re.config.AppWidgetSet")
public class RootUI extends UI {
    @Autowired
    private SpringViewProvider springViewProvider;

    public RootUI() {
    }

    @Override
    protected void init(VaadinRequest request) {
        CommonHelper.BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getPath();
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(springViewProvider);
        navigator.navigateTo(LoginView.NAME);
        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                final SecurityContext securityContext = SecurityContextHolder.getContext();
                final Authentication authentication = securityContext.getAuthentication();

                boolean isLoggedIn = authentication != null && authentication.isAuthenticated();
                boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            }
        });
        setNavigator(navigator);
    }
}
