package com.eteach.eteach.http.response;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

public class JwtAuthenticationResponse extends ApiResponse implements Serializable {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private Set<? extends GrantedAuthority> grantedAuthorities;

    public JwtAuthenticationResponse(String accessToken,
                                     String username,
                                     Set<? extends GrantedAuthority> grantedAuthorities,
                                     HttpStatus status,
                                     String message) {
        super(status, message);
        this.accessToken = accessToken;
        this.username = username;
        this.grantedAuthorities = grantedAuthorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}