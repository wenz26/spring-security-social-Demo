package com.cwz.springboot.sercuritydemo.browser.controller;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.browser.support.SimpleResponse;
import com.cwz.springboot.sercuritydemo.social.qq.browser.support.SocialUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /* 当springboot请求发生跳转时，会跳到.loginPage("/authentication/require")这个请求
     * 但在跳转这个请求之前，他会把当前的这个请求加到HttpSessionRequestCache里(其实就是存在session里)
     * 当需要用的时候再从requestCache(其实就是从session里取)里取
     *
     * 如：当发送localhost:8080/user这个请求，他会把请求存在session里，再去跳转.loginPage(....)
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /*
     * RedirectStrategy: 重定向策略
     *
     * */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /*
     * 当身份认证时，跳转到这里
     * */
    // requireAuthentication: 请求身份认证
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 如果不是.html请求就返回一个未授权的状态码(401)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws IOException {

        /* SavedRequest就是之前缓存的这个请求 (如：localhost:8080/user)
         *  requestCache.getRequest(request,response);这样就拿到了引发跳转的这个请求
         * */
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // 拿到请求跳转的那个url
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + targetUrl);

            // 如果引发跳转的请求以.html结尾，那么直接把他跳到登录页上
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response,
                        securityProperties.getBrowserProperties().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页！" + e);
    }

    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();

        // 这里可以从session里拿到一个Connection，这个Connection就是我们所授权到的用户信息
        Connection<?> connection =
                providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }

    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) // session过期就返回一个未授权的状态码(401)需要重新认证
    public SimpleResponse sessionInvalid(HttpServletRequest request) {

        String message = "session失效了！！！";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                cookie.setMaxAge(0);
            }
        }
        return new SimpleResponse(message);
    }

}
