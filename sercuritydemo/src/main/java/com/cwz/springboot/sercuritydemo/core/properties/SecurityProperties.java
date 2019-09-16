package com.cwz.springboot.sercuritydemo.core.properties;

import com.cwz.springboot.sercuritydemo.social.qq.core.properties.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

//@PropertySource(value = {"classpath:security.properties"})
@ConfigurationProperties("com.cwz.security") // 这里用的是application.properties
public class SecurityProperties {

    /* 这里的security.properties里写的com.cwz.security.browserProperties.loginPage
     *  要与以下定义的字段名一致 browserProperties 里的 loginPage
     *  */
    private BrowserProperties browserProperties = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    public BrowserProperties getBrowserProperties() {
        return browserProperties;
    }

    public void setBrowserProperties(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }
}
