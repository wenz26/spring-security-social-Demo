package com.cwz.springboot.sercuritydemo.demo.authorize;


import com.cwz.springboot.sercuritydemo.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component("demoAuthorizeConfigProvider")
@Order(Integer.MAX_VALUE) // 读取顺序 最大值，在集合里排在最后面
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        // 这里同样适用于页面映射
        // config.antMatchers("/user").hasRole("ADMIN");

        config.anyRequest().access("@rbacService.hasPermission(request, authentication)");
    }
}
