package com.re.views;

import com.re.components.login.LoginForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.spring.navigator.VaadinView;

@VaadinView(name = "login")
public class LoginView extends VerticalLayout implements View {
    public static final String NAME = "login";

    public LoginView(LoginForm loginForm) {
        addComponent(loginForm);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
