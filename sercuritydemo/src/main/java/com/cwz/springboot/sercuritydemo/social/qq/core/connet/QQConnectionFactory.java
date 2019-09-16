package com.cwz.springboot.sercuritydemo.social.qq.core.connet;

import com.cwz.springboot.sercuritydemo.social.qq.core.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/*
 * 泛型为Api的接口类
 * 这里会自动生成包装成 Connection数据
 * */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /*
     * QQConnectionFactory(String providerId, OAuth2ServiceProvider<QQ> serviceProvider,
     *                           ApiAdapter<QQ> apiAdapter)
     *
     * QQConnectionFactory有三个参数：
     * providerId：提供商的唯一标识，通过配置文件配置进来，
     * serviceProvider：其实就是QQServiceProvider
     * apiAdapter：其实就是QQAdapter
     * */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
