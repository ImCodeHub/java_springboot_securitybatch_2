package com.example.java.springboot.securitybatch2.Entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    ADMIN,
    HR,
    USER,
    MANAGER;

    public List<SimpleGrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.name())); // "ROLE_ADMIN","ROLE_HR","ROLE_USER"
    }
}
