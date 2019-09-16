package com.cwz.springboot.sercuritydemo.app.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig /*extends AuthorizationServerConfigurerAdapter */ {

    /*@Bean("myPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(bCryptPasswordEncoder().encode("123"))
                .secret(bCryptPasswordEncoder().encode("123"))
                .redirectUris("http://www.czodly.top")
                .scopes("all")
                .authorizedGrantTypes("ROLE_USER");
    }*/
}
