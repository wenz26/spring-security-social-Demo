package com.cwz.springboot.sercuritydemo.social.qq.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component("connectionSignUp")
public class DemoConnectionSignUp implements ConnectionSignUp {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String execute(Connection<?> connection) {

        logger.info("----------------社交用户信息：" + connection);

        // 根据社交用户信息(也即是Connection里的信息) 默认创建用户并返回用户唯一标识(这里用的是用户昵称)
        return connection.getDisplayName();
    }
}
