package com.cwz.springboot.token_demo.social.qq.config;

import com.cwz.springboot.token_demo.social.qq.binding.MyConnectView;
import com.cwz.springboot.token_demo.social.qq.connet.QQConnectionFactory;
import com.cwz.springboot.token_demo.social.qq.properties.SecurityProperties;
import com.cwz.springboot.token_demo.social.qq.properties.qq.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

@Configuration
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {

        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {

        QQProperties qqConfig = securityProperties.getSocialqq().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

    @Bean("connect/qqConnected")
    // @ConditionalOnMissingBean(MyConnectView.class)
    public View qqConnectedView(){
        return new MyConnectView();
    }


}
