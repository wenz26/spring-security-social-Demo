package com.cwz.springboot.sercuritydemo.browser.logout;

import com.cwz.springboot.sercuritydemo.browser.support.SimpleResponse;
import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private String stringOutUrl;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    public MyLogoutSuccessHandler(String stringOutUrl) {
        this.stringOutUrl = stringOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        logger.info("退出成功");


        if (StringUtils.isBlank(stringOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功！！！")));
        } else {
            response.sendRedirect(stringOutUrl);
        }
    }
}
