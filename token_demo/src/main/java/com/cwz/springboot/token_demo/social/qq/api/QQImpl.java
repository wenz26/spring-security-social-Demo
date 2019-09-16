package com.cwz.springboot.token_demo.social.qq.api;

import com.alibaba.fastjson.JSONObject;
import com.cwz.springboot.token_demo.social.qq.Info.QQUserInfo;
import com.cwz.springboot.token_demo.social.qq.config.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 申请QQ登录成功后，分配给应用的appid，相当于用户名
    private String appId;

    // 用户的ID，与QQ号码一一对应。
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());


    //accessToken和restTemplate父类里面有，所以这里就不用配
    public QQImpl(String accessToken, String appId){


        // 这里调用父类的构造方法：用父类的restTemplate发请求的时候，会把accessToken的值以access_token的名字写到请求里
        // 其实就是加到https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s&access_token=accessToken里
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(Constant.URL_GET_OPENID, accessToken);

        // 发请求 (父类提供) 其实就是发请求得到openId
        String result = getRestTemplate().getForObject(url, String.class);

        logger.info("实例化QQApi后，获取的openId为：" + result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    @Override
    public QQUserInfo getQQUserInfo() {

        String url = String.format(Constant.URL_GET_USERINFO, appId, openId);

        String result = getRestTemplate().getForObject(url, String.class);

        logger.info("实例化QQApi后，获取用户信息为：" + result);

        QQUserInfo userInfo = null;

        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            logger.info("实例化QQApi后，获取的userInfo为：" + JSONObject.toJSONString(userInfo));
            return userInfo;
        }catch (IOException e) {
            throw new RuntimeException("获取用户信息失败" + e);
        }

    }
}
