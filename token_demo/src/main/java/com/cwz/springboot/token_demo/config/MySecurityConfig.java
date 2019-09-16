package com.cwz.springboot.token_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSocialConfigurer socialSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/mylogin.html")
                .loginProcessingUrl("/doLogin")
                .and()
            .authorizeRequests()
                .antMatchers("/mylogin.html", "/doLogin",
                        "/register", "/myregister.html", "/qqRegister", "/qqregister.html").permitAll()
                .anyRequest().authenticated()
                .and()
            .apply(socialSecurityConfig)
                .and()
            .csrf().disable();

    }
}
