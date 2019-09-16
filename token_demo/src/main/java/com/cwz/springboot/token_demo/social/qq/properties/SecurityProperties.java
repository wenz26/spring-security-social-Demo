package com.cwz.springboot.token_demo.social.qq.properties;

import com.cwz.springboot.token_demo.social.qq.properties.qq.SocialQQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("com.cwz.security")
public class SecurityProperties {

    private SocialQQProperties socialqq = new SocialQQProperties();

    public SocialQQProperties getSocialqq() {
        return socialqq;
    }

    public void setSocialqq(SocialQQProperties socialqq) {
        this.socialqq = socialqq;
    }

}
