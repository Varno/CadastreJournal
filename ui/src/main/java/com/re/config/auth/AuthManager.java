package com.re.config.auth;

import com.re.auth.CustomAuthenticationImpl;
import com.re.auth.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vaadin.spring.security.Security;

import java.util.Collection;

@Component
public class AuthManager implements AuthenticationManager {
    private UserService userService;

    public AuthManager(UserService userService) {
        this.userService = userService;
    }

    public AuthManager() {
    }

    @Override
    public Authentication authenticate(Authentication srcAuth) throws AuthenticationException {
        CustomAuthenticationImpl auth = (CustomAuthenticationImpl)srcAuth;
        if (auth != null) {
            String username = (String) auth.getPrincipal();
            String password = (String) auth.getCredentials();
            String ipAddress = (String) auth.getUserIpAddress();
            UserDetails user = userService.loadUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                auth = new CustomAuthenticationImpl(username, password, ipAddress, authorities);
                return auth;
            }
        }

        throw new BadCredentialsException("Указаны неверные логин/пароль");
    }
}
