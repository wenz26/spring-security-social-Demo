package com.cwz.springboot.sercuritydemo.social.qq.core.properties;

import com.cwz.springboot.sercuritydemo.social.config.SocialProperties;

public class QQProperties extends SocialProperties {

    // 服务提供商的Id
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
