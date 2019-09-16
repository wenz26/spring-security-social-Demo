package com.cwz.springboot.sercuritydemo.core.validate.code.Sms;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.core.validate.code.Image.ImageCode;
import com.cwz.springboot.sercuritydemo.core.validate.code.ValidateCodeController;
import com.cwz.springboot.sercuritydemo.core.validate.code.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
 * OncePerRequestFilter: spring提供的一个工具类，保证过滤器每次只会被调用一次
 *
 * InitializingBean: 实现这个接口可以在其他参数都组装完毕后，初始化urls这个值
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，
 * 凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 * */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 设置一个集合来存放需要拦截的url
    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    // AntPathMatcher: 在做uri匹配规则发现这个类，根据源码对该类进行分析，它主要用来做类URLs字符串匹配
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        /* splitByWholeSeparatorPreserveAllToKens方法及splitPreserveAllToKens方法作用相同：
         *  分割字符串过程中会按照每个分隔符进行分割，不忽略任何空白项；
         */
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getSms().getUrl(), ",");

        if (configUrls != null) {
            for (String configUrl : configUrls) {
                urls.add(configUrl);
                System.out.println(configUrl);
            }
        }
        urls.add("/authentication/mobile");

    }

    /* 逻辑处理 */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 如果传进来的URI和我们配的urls匹配的上，就进行过滤
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        // 处理表单提交的请求，也就是只有在登录时才生效，也就是我请求的是登录的这个URI的话并且是post请求
        if (action) {

            try {
                // 做校验，校验验证码，传个request，因为要在session里拿东西
                validate(new ServletWebRequest(request));

            } catch (ValidateCodeException e) {
                // 在验证方法捕获到异常后用自定义的身份验证失败处理器处理异常
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);

                // 处理失败后，return，不往下走
                return;
            }

        }

        // 如果不是登录请求，就不做任何处理，直接调用后面的过滤器
        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        // 从session中拿出我们之前放进去的SESSION_KEY
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SESSION_KEY_PREFIX_SMS);

        // 从我们请求里拿到登录页面(signIn.html)，的imageCode输入框(input)里的输入的值
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY_PREFIX_SMS);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY_PREFIX_SMS);

    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
