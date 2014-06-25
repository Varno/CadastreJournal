package com.re.config;

import com.re.components.login.LoginForm;
import com.re.config.auth.AuthManager;
import com.re.config.auth.LoginFormListener;
import com.re.config.auth.UserService;
import com.re.views.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.vaadin.spring.UIScope;

@EnableGlobalMethodSecurity
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    Environment environment;

    @Bean
    @UIScope
    public LoginView loginView(){
        return new LoginView(loginForm());
    }

    @Bean
    public AuthManager authManager() {
        return new AuthManager(userService());
    }

    @Bean
    public UserService userService(){
        return new UserService(environment);
    }

    @Bean
    public LoginFormListener loginFormListener(){
       return new LoginFormListener(authManager());
    }

    @Bean
    public LoginForm loginForm(){
        return new LoginForm(loginFormListener());
    }


}
