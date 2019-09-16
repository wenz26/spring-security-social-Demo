package com.cwz.springboot.token_demo.social.qq.config;

import com.cwz.springboot.token_demo.social.qq.config.filter.SocialAuthenticationFilterPostProcessor;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    MySpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    // 这里的object 就是要放到过滤器链上的filter
    @Override
    protected <T> T postProcess(T object) {

        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);

        filter.setFilterProcessesUrl(filterProcessesUrl);

        if (socialAuthenticationFilterPostProcessor != null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }

        return (T) filter;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
