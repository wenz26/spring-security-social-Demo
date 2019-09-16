package com.cwz.springboot.sercuritydemo.core.authorize;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {

    // 告诉spring能不能访问
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
