package com.example.userauthenticationsystem.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RestAuthenticationToken extends AbstractAuthenticationToken {

    private final Object credentials;
    private final Object principal;

    public RestAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object credentials, Object principal) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
        setAuthenticated(false);
    }

    public RestAuthenticationToken(Object credentials, Object principal) {
        super(null);
        this.credentials = credentials;
        this.principal = principal;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
