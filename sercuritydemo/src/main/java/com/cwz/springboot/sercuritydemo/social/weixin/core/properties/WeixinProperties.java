package com.cwz.springboot.sercuritydemo.social.weixin.core.properties;


import com.cwz.springboot.sercuritydemo.social.config.SocialProperties;

public class WeixinProperties extends SocialProperties {

    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
