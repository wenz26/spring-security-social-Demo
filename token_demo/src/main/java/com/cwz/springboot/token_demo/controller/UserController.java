package com.cwz.springboot.token_demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.cwz.springboot.token_demo.jpa.entity.User;
import com.cwz.springboot.token_demo.jpa.repository.UserRepository;
import com.cwz.springboot.token_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/register")
    public String register(User user){

        logger.info("获取的User对象：" + JSONObject.toJSONString(user));

        if (user.getUsername() != null && !user.getUsername().equals("")
                && user.getPassword() != null && !user.getPassword().equals("")){
            userService.addUser(user);
        } else {
            user = null;
        }

        return user == null ? "注册失败" : "注册成功";
    }

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @GetMapping("/getUser")
    public List<User> getCurrentUser(){
        return userRepository.findAll();
    }
}
