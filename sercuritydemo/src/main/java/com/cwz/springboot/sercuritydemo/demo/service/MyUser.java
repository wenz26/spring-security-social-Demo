package com.cwz.springboot.sercuritydemo.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

public class MyUser extends SocialUser {

    private com.cwz.springboot.sercuritydemo.demo.entity.User user;

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities,
                  com.cwz.springboot.sercuritydemo.demo.entity.User user) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                  com.cwz.springboot.sercuritydemo.demo.entity.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public com.cwz.springboot.sercuritydemo.demo.entity.User getUser() {
        return user;
    }

}
