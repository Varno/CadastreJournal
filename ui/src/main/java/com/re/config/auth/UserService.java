package com.re.config.auth;

import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {
    private Environment environment;

    public UserService(Environment environment) {
        this.environment = environment;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        String uname = environment.getProperty("auth.login");
        String pass = environment.getProperty("auth.password");
        if (uname.equals(username)) {
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
            User user = new User(username, pass, true, true, false, false, authorities);
            return user;
        }

        return null;
    }
}