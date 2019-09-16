package com.cwz.springboot.sercuritydemo.browser.config;

import com.cwz.springboot.sercuritydemo.browser.session.MyExpiredSessionStrategy;
import com.cwz.springboot.sercuritydemo.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.cwz.springboot.sercuritydemo.core.authorize.AuthorizeConfigManager;
import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.SmsCodeFilter;
import com.cwz.springboot.sercuritydemo.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer cwzSocialSecurityConfig;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Bean(name = "passwordEncoder")
    /* 密码加密解密 */
    public PasswordEncoder passwordEncoder() {

        // BCryptPasswordEncoder该类是PasswordEncoder接口的实现类
        return new BCryptPasswordEncoder();
    }

    @Bean
    /* 配置一个PersistentTokenRepository来读写数据库(读写Token) */
    public PersistentTokenRepository persistentTokenRepository() {

        /*
         * 使用数据库存储自动登录相关用户凭证及相关信息的方式，不需要任何的代码，只需要进行简单的
         * 配置就可达到这样的效果，因为Spring Security已经为我们提供了这样的类，
         * 类名为JdbcTokenRepositoryImpl， 查看该类的源码会发现，该类包含了创建持久化登录信息的表语句，还有插入，修改，删除语句。
         * */
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        //这里配置了 在spring容器启动时，自动在数据库里建一张token的表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 把请求失败处理器换成自己的失败处理器
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        // 把请求失败处理器换成自己的失败处理器
        smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();


        // 把自个写的ValidateCodeFilter过滤器加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 这个配置相当于把SmsCodeAuthenticationSecurityConfig类写的configure()也加到浏览器这个配置的后面
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(cwzSocialSecurityConfig) // 把我当前的过滤器上加一个SocialAuthenticationFilter过滤器
                .and()

                .formLogin() // 表单登录
//      http.httpBasic() // 基础认证登录
                //指定登录页的路径
                .loginPage("/authentication/require")

                /*
                 * 当我写入这个请求 security就会自动让UsernamePasswordAuthenticationFilter这个类来处理这个请求
                 * UsernamePasswordAuthenticationFilter这个请求会自动获取username和password
                 */
                // 指定自定义form表单请求的路径
                .loginProcessingUrl("/authentication/form")
                //.loginProcessingUrl("/regist")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailHandler)
                //.failureUrl("/signIn.html?error")
                //.defaultSuccessUrl("/success")

                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/imooc-signIn.html这个page。
                .permitAll()

                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // token的有效时间
                .tokenValiditySeconds(securityProperties.getBrowserProperties().getRememberMeSeconds())
                .userDetailsService(userDetailsService)

                .and()
                .sessionManagement()
                // session失效跳转页面
                .invalidSessionUrl("/session/invalid")
                // 最大的session数量为1，也就是同一个用户后面登录的session会把之前登录的session给覆盖掉
                .maximumSessions(1)
                // 当session达到最大时，阻止后来的登录行为
                //.maxSessionsPreventsLogin(true)
                // 同一个用户后面登录的session会把之前登录的session给覆盖掉，过期请求的处理
                .expiredSessionStrategy(new MyExpiredSessionStrategy())
                .and()
                .and()

                .logout()
                //.logoutUrl("/signOut")

                // 配置logoutSuccessHandler那么logoutSuccessUrl就不会生效了
                //.logoutSuccessUrl("/cwz-logout.html")
                .logoutSuccessHandler(logoutSuccessHandler)
                // 退出时删除cookie的"JSESSIONID" 浏览器会判断这个"JSESSIONID"来用不用设置session
                .deleteCookies("JSESSIONID")
                .and()

                /*.authorizeRequests()// 对请求做授权
                .antMatchers("/authentication/require",
                        securityProperties.getBrowserProperties().getLoginPage(),
                        "/code/image", "/code/sms/*", "/regist","/session/invalid",
                        securityProperties.getBrowserProperties().getSignUpUrl(),
                        securityProperties.getBrowserProperties().getSignOutUrl())
                .permitAll()// 当访问这些url时不用身份验证
                // 配置权限，只有ADMIN的用户权限才能访问/user
                .antMatchers(HttpMethod.GET, "/user/*").hasRole("ADMIN")
                //.antMatchers(HttpMethod.GET, "/user/*").access("hasRole('ADMIN') and hasIpAddress('xxx')")
                .anyRequest()// 任何请求
                .authenticated()// 都需要身份验证
                .and()*/
                .csrf().disable();// 关闭跨站伪造防护

        authorizeConfigManager.config(http.authorizeRequests());

    }


}
