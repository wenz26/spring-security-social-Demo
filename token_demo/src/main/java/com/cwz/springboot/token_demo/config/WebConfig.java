package com.cwz.springboot.token_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;

@Component
public class WebConfig {

    /*
     * RequestContextListener监听器
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
