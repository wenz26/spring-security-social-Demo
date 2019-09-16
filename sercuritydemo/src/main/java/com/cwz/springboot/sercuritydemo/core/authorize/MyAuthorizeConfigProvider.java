package com.cwz.springboot.sercuritydemo.core.authorize;


import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component("authorizeConfigProvider")
@Order(Integer.MIN_VALUE) // 读取顺序 最小值，在集合里排在最前面
public class MyAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config.antMatchers("/authentication/require",
                securityProperties.getBrowserProperties().getLoginPage(),
                "/code/image", "/code/sms/*", "/regist", "/session/invalid",
                securityProperties.getBrowserProperties().getSignUpUrl(),
                securityProperties.getBrowserProperties().getSignOutUrl())
                .permitAll();


    }
}
