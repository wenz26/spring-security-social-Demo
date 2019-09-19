package com.cwz.springboot.token_demo.social.qq.controller;

import com.alibaba.fastjson.JSONObject;
import com.cwz.springboot.token_demo.jpa.entity.User;
import com.cwz.springboot.token_demo.service.UserService;
import com.cwz.springboot.token_demo.social.qq.Info.SocialUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@RestController
public class QQController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Autowired
    private ConnectController connectController;*/

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @PostMapping("/qqRegister")
    public String regist(User user, HttpServletRequest request){

        logger.info("获取的User对象：" + JSONObject.toJSONString(user));

        String userId = user.getUsername();


        if (user.getUsername() != null && !user.getUsername().equals("")
                && user.getPassword() != null && !user.getPassword().equals("")){

            /*Random random = new Random();
            String password = String.valueOf(random.nextLong());
            logger.info("随机生成的密码为：" + password);

            user.setPassword(passwordEncoder.encode(password));*/

            userService.addUser(user);
            providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        } else {
            user = null;
        }

        return user == null ? "qq登录失败" : "qq登录成功";
    }

    @GetMapping("/social/signUp")
    public String socialSignUp(HttpServletRequest request) throws IOException {
        String uuid = UUID.randomUUID().toString();
        SocialUserInfo userInfo = new SocialUserInfo();

        /*Connection<?> connectionFromSession =
                providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));*/

        SecurityContextImpl sessionImpl =
                (SecurityContextImpl)sessionStrategy
                        .getAttribute(new ServletWebRequest(request), "SPRING_SECURITY_CONTEXT");

        String sessionString = JSONObject.toJSONString(sessionImpl);

        JSONObject jsonObject = JSONObject.parseObject(sessionString)
                .getJSONObject("authentication").getJSONObject("connection");

        logger.info("获取的connection信息为：" + jsonObject.toJSONString());

        userInfo.setHeadImg(jsonObject.getString("imageUrl"));
        userInfo.setNickname(jsonObject.getString("displayName"));
        userInfo.setProviderId(jsonObject.getJSONObject("key").getString("providerId"));
        userInfo.setProviderUserId(jsonObject.getJSONObject("key").getString("providerUserId"));

        String user = JSONObject.toJSONString(userInfo);
        return user;

//        socialRedisHelper.saveConnectionData(uuid, connectionFromSession.createData());
//        response.sendRedirect(bindUrl + "?mkey=" + uuid);
    }

    /*@PostMapping("/connect/qq")
    public void getConnect(HttpServletRequest request){
        connectController.connect("qq", new ServletWebRequest(request));
        connectController.oauth2Callback("qq", new ServletWebRequest(request));
    }*/
}
