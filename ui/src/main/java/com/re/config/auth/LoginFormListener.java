package com.re.config.auth;

import com.re.components.login.LoginForm;
import com.re.ui.RootUI;
import com.re.views.RealEstateView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginFormListener implements Button.ClickListener {
    private AuthManager authManager;

    public LoginFormListener(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();
            LoginForm parent = (LoginForm) source.getParent().getParent();
            String username = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();
            UsernamePasswordAuthenticationToken request = new
                    UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            RootUI current = (RootUI) UI.getCurrent();
            Navigator navigator = current.getNavigator();
            navigator.navigateTo(RealEstateView.NAME);
        } catch (AuthenticationException e) {
            Notification.show("Ошибка: " + e.getMessage());
        }
    }
}
