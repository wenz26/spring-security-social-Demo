package com.cwz.springboot.token_demo.social.qq.connet;

import com.cwz.springboot.token_demo.social.qq.api.QQ;
import com.cwz.springboot.token_demo.social.qq.api.QQImpl;
import com.cwz.springboot.token_demo.social.qq.config.Constant;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, Constant.URL_AUTHORIZE, Constant.URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
