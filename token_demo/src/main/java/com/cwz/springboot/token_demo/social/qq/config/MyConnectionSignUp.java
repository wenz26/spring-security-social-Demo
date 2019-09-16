package com.cwz.springboot.token_demo.social.qq.config;

import com.alibaba.fastjson.JSONObject;
import com.cwz.springboot.token_demo.jpa.entity.User;
import com.cwz.springboot.token_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("connectionSignUp")
public class MyConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String execute(Connection<?> connection) {

        logger.info("获取的社交connection为：" + JSONObject.toJSONString(connection));

        addUser(connection);

        return connection.getDisplayName();
    }

    private void addUser(Connection<?> connection){
        Random random = new Random();
        String password = String.valueOf(random.nextLong());
        logger.info("随机生成的密码为：" + password);

        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(connection.getDisplayName());

        userService.addUser(user);
        logger.info("社交用户注册成功！！！  用户名为：" + user.getUsername());
    }


}
