package com.cwz.springboot.token_demo.social.qq.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("socialAuthenticationFilterPostProcessor")
public class MySocialAuthenticationFilterPostProcessor implements  SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    // 后处理器
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
    }
}
