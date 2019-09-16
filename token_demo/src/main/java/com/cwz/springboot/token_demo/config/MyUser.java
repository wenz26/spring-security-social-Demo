package com.cwz.springboot.token_demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Deprecated
public class MyUser extends User {

    private com.cwz.springboot.token_demo.jpa.entity.User user;

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities,
                  com.cwz.springboot.token_demo.jpa.entity.User user) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                  com.cwz.springboot.token_demo.jpa.entity.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public com.cwz.springboot.token_demo.jpa.entity.User getUser() {
        return user;
    }

}
