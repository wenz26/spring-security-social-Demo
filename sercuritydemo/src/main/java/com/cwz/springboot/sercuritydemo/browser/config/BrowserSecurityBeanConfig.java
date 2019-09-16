package com.cwz.springboot.sercuritydemo.browser.config;

import com.cwz.springboot.sercuritydemo.browser.logout.MyLogoutSuccessHandler;
import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(MyLogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogoutSuccessHandler(securityProperties.getBrowserProperties().getSignOutUrl());
    }

}
