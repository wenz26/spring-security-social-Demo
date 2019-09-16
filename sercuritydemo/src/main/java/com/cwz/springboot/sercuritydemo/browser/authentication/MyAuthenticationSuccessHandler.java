package com.cwz.springboot.sercuritydemo.browser.authentication;

import com.alibaba.fastjson.JSONObject;
import com.cwz.springboot.sercuritydemo.core.enums.LoginType;
import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myAuthenticationSuccessHandler")
/* 我们想实现自定义的用户成功登陆处理，只需要实现AuthenticationSuccessHandler接口即可 */
public class MyAuthenticationSuccessHandler /*implements AuthenticationSuccessHandler*/
        extends SavedRequestAwareAuthenticationSuccessHandler {
    // 这里不实现AuthenticationSuccessHandler接口而是继承springboot的一个默认的成功处理器

    private Logger logger = LoggerFactory.getLogger(getClass());

    // ObjectMapper 是一个使用 Swift 编写的用于 model 对象（类和结构体）和 JSON 之间转换的框架。
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    // authentication: 认证信息
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("登录成功");

        System.out.println(securityProperties.getBrowserProperties().getLoginType());
        // 如果是JSON处理结果就我们自己来处理，如果不是按springboot的默认处理器来处理，执行跳转
        if (LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())) {


            /*String aaa = objectMapper.writeValueAsString(authentication.getDetails());
            System.out.println((objectMapper.writeValueAsString(authentication.getDetails())).getClass());
            JSONObject jsonObject = JSONObject.parseObject(aaa);
            System.out.println(jsonObject);
            String sessionId = jsonObject.getString("sessionId");
            System.out.println(sessionId);
            SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
            Object attribute1 = sessionStrategy.getAttribute(new ServletWebRequest(request), sessionId);
            Object attribute2 = sessionStrategy.getAttribute(new ServletWebRequest(request), "sessionId");
            System.out.println(attribute1);
            System.out.println(attribute2);*/

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
