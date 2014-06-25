package com.re.components.login;

import com.re.config.auth.LoginFormListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.*;

import javax.swing.*;

public class LoginForm extends VerticalLayout {
    private TextField nameField = new TextField("Логин: ");
    private PasswordField passwordField = new PasswordField("Пароль: ");
    private Button btnLogin = new Button("Вход");

    public LoginForm(LoginFormListener loginFormListener) {
        final FormLayout loginlayout = new FormLayout();
        loginlayout.setWidth("350px");
        loginlayout.setHeight("150px");
        loginlayout.addComponent(nameField);
        loginlayout.addComponent(passwordField);
        loginlayout.setComponentAlignment(nameField, Alignment.MIDDLE_CENTER);
        loginlayout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        loginlayout.addComponent(btnLogin);
        loginlayout.setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
        setSizeFull();
        addComponent(loginlayout);
        setComponentAlignment(loginlayout, Alignment.TOP_CENTER);
        btnLogin.addClickListener(loginFormListener);
        btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public TextField getTxtLogin() {
        return nameField;
    }
    public PasswordField getTxtPassword() {
        return passwordField;
    }
}
