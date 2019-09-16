package com.cwz.springboot.token_demo.social.qq.config;


import com.cwz.springboot.token_demo.social.qq.config.filter.SocialAuthenticationFilterPostProcessor;
import com.cwz.springboot.token_demo.social.qq.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@Order(1)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ConnectionSignUp connectionSignUp;

    // 后处理器
    @Autowired
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

        /*
         * new JdbcUsersConnectionRepository()有3个参数
         * dataSource：数据源，注入。
         *
         * connectionFactoryLocator：这个类(参数)的作用是根据条件查找适合我们的connectionFactory，
         * 因为我们会有很多的connectionFactory，用connectionFactory构建Connection数据。
         *
         * Encryptors：加解密工具，帮你把插入到数据库里的数据做一个加解密，Encryptors.noOpText()是不作任何操作，原值存入
         * */
        JdbcUsersConnectionRepository repository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        if(connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
            logger.info("获取的connectionSignUp为：" + connectionSignUp);
        }

        return repository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    // social要加到spring security链上的过滤器
    @Bean("socialSecurityConfig")
    @Order(Integer.MIN_VALUE)
    public SpringSocialConfigurer getSocialSecurityConfig() {

        String filterProcessesUrl = securityProperties.getSocialqq().getFilterProcessesUrl();
        MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);

        // 1、认证失败跳转注册页面
        // 跳转到signUp controller，从session中获取用户信息并通过生成的uuid保存到redis里面，然后跳转bind页面
        // 前端绑定后发送用户信息到后台bind controller，
        // 1）保存到自己系统用户；
        // 2）保存一份UserConnection表数据，Spring Social通过这里面表数据进行判断是否绑定
        configurer.signupUrl("/qqregister.html");

        //2、认证成功跳转后处理器，跳转带token的成功页面
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);

        return configurer;
    }

    /*
     * 1.在注册过程中如何拿到social的信息
     * 2.注册完成了如何把业务系统的用户Id传回给spring social
     *
     * ConnectionFactoryLocator: 用来定位ConnectionFactory的
     *
     * 从Session中获取社交账号信息
     * */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){

        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));

    }

}
