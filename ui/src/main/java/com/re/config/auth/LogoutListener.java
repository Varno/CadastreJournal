package com.re.config.auth;

import com.re.views.LoginView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.security.core.context.SecurityContextHolder;

public class LogoutListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        logout();
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
        Navigator navigator = UI.getCurrent().getNavigator();
        navigator.navigateTo(LoginView.NAME);
    }
}
