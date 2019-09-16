package com.cwz.springboot.sercuritydemo.social.qq.core;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public MySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    // 这里的object 就是要放到过滤器链上的filter
    @Override
    protected <T> T postProcess(T object) {

        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);

        filter.setFilterProcessesUrl(filterProcessesUrl);

        return super.postProcess(object);
    }
}
