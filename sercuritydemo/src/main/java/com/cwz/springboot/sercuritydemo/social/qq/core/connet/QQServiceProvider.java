package com.cwz.springboot.sercuritydemo.social.qq.core.connet;

import com.cwz.springboot.sercuritydemo.social.qq.core.api.QQ;
import com.cwz.springboot.sercuritydemo.social.qq.core.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/*
 * QQ服务提供商 继承AbstractOAuth2ServiceProvider(提供商类)
 * AbstractOAuth2ServiceProvider类有一个泛型，这个泛型是Api接口的类型
 * */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    // 将用户导向认证服务器的url，用户在这个地址上点确认进行授权
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    // 通过授权码获取令牌，拿授权码申请令牌时的url
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {

        /*
         * OAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl)
         * 这里有四个参数: appId(clientId: 相当于用户名), appSecret(clientSecret: 相当于密码),
         * authorizeUrl(将用户导向认证服务器的url), accessTokenUrl(拿授权码申请令牌时的url)
         *  */
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }


    // 当需要Api实现类的时候，就new一个出来
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
