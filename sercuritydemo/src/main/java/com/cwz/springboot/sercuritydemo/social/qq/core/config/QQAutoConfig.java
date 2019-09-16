package com.cwz.springboot.sercuritydemo.social.qq.core.config;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.social.config.SocialAutoConfigurerAdapter;
import com.cwz.springboot.sercuritydemo.social.qq.core.connet.QQConnectionFactory;
import com.cwz.springboot.sercuritydemo.social.qq.core.properties.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component

/*
 * 这个注解的作用：只有在spring里的配置文件中配置了com.cwz.security.social.qq的appId时这个配置类才会生效
 * */
@ConditionalOnProperty(prefix = "com.cwz.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

}
