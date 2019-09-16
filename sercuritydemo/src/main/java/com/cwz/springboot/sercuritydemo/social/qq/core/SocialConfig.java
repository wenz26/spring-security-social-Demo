package com.cwz.springboot.sercuritydemo.social.qq.core;


import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.social.qq.demo.security.DemoConnectionSignUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/*
 * 社交配置的适配器
 * 包括把 Connection数据保存到数据库的一些配置(spring替我们写好了，只需配置一下JdbcUsersConnectionRepository)
 * */
@Configuration

// 把我们spring social社交项目相应的一些特性起起来
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * @Autowired(required = false)，这等于告诉 Spring：在找不到匹配 Bean 时也不报错。
     * 因为这个不是在每个系统中都会实现
     * */
    @Autowired/*(required = false)*/
    private ConnectionSignUp connectionSignUp;


    // 返回一个JdbcUsersConnectionRepository
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

        /*
         * 这里可以点这个类JdbcUsersConnectionRepository，找到该类的包，里面有个建表语句
         * */
        JdbcUsersConnectionRepository repository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        // 这里可以设表的前缀
        // repository.setTablePrefix("cwz_");


        // 如果connectionSignUp不等于空就把connectionSignUp设进去

        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
            logger.info("获取的connectionSignUp为：" + connectionSignUp);
        }

        return repository;
    }

    // social要加到spring security链上的过滤器
    @Bean("cwzSocialSecurityConfig")
    public SpringSocialConfigurer cwzSocialSecurityConfig() {

        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);

        configurer.signupUrl(securityProperties.getBrowserProperties().getSignUpUrl());
        return configurer;
    }

    /*
     * 1.在注册过程中如何拿到social的信息
     * 2.注册完成了如何把业务系统的用户Id传回给spring social
     *
     * ConnectionFactoryLocator: 用来定位ConnectionFactory的
     * */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));

    }

}
