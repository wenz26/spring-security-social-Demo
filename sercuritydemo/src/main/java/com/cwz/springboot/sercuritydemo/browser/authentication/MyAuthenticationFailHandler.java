package com.cwz.springboot.sercuritydemo.browser.authentication;

import com.cwz.springboot.sercuritydemo.browser.support.SimpleResponse;
import com.cwz.springboot.sercuritydemo.core.enums.LoginType;
import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myAuthenticationFailHandler")
/* 我们想实现自定义的用户失败登陆处理，只需要实现AuthenticationFailureHandler接口即可 */
public class MyAuthenticationFailHandler /*implements AuthenticationFailureHandler*/
        extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        logger.info("登录失败");


        // 如果是JSON处理结果就我们自己来处理，如果不是按springboot的默认处理器来处理，执行跳转
        if (LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");

            // 这里只返回错误的消息
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, e);
        }

    }
}
