package com.re.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationImpl extends UsernamePasswordAuthenticationToken implements CustomAuthentication {
    private String userIpAddress;
    private Boolean _isAuthenticated;

    public CustomAuthenticationImpl(java.lang.Object principal, java.lang.Object credentials, String ipAddress)
    {
        super(principal, credentials);
        this.userIpAddress = ipAddress;
     }

    public CustomAuthenticationImpl(java.lang.Object principal, java.lang.Object credentials, String ipAddress,
                        java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities)
    {
        super(principal, credentials);
        this.userIpAddress = ipAddress;
        this.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws java.lang.IllegalArgumentException {
        this._isAuthenticated = isAuthenticated;
    }

    @Override
    public boolean isAuthenticated() {
        return this._isAuthenticated;
    }

    public String getUserIpAddress() {
        return this.userIpAddress;
    }
}