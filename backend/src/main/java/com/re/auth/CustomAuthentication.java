package com.re.auth;

import org.springframework.security.core.Authentication;

public interface CustomAuthentication extends Authentication {
    String getUserIpAddress();
}