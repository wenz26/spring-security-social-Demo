package com.cwz.springboot.token_demo.social.qq.properties.qq;

import com.cwz.springboot.token_demo.social.qq.properties.SocialProperties;

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
