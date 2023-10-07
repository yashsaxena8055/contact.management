package com.example.contact.management.security.service;

import  java.util.List;
import com.example.contact.management.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class UserDetail implements UserDetails {
    private Integer userId;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    public UserDetail(User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = setAuthority();

    }
    private List<GrantedAuthority> setAuthority(){
        authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SWAGGER"));
        return authorities;
    }
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
       return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
