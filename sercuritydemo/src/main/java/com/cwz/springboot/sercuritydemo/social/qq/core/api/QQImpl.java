package com.cwz.springboot.sercuritydemo.social.qq.core.api;

import com.cwz.springboot.sercuritydemo.social.qq.core.entity.QQUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/*
 * 在写social第三方登录时，所有的api都应该继承AbstractOAuth2ApiBinding
 * 用于 执行第6步(步骤在markdown里) 获取用户信息 (其里面有前5步的所获取的Token，不过主要是用于获取用户信息)
 * */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 获取QQ用户信息时的路径
    private static final String URL_GET_USERINFO =
            "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    // 通过accessToken去拿openId发的请求的路径
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    // 申请QQ登录成功后，分配给应用的appid，相当于用户名
    private String appId;

    /* 用户的ID，与QQ号码一一对应。可通过调用
     * https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
     */
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());


    /*
     * accessToken和restTemplate父类里面有，所以这里就不用配
     * */

    public QQImpl(String accessToken, String appId) {

        // 这里调用父类的构造方法：用父类的restTemplate发请求的时候，会把accessToken的值以access_token的名字写到请求里
        // 其实就是加到https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s&access_token=accessToken里
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        // 这个写法就是把URL_GET_OPENID的%s替换为accessToken的值
        String url = String.format(URL_GET_OPENID, accessToken);

        // 发请求 (父类提供) 其实就是发请求得到openId
        String result = getRestTemplate().getForObject(url, String.class);

        logger.info("实例化 QQApi，获取的openId为：" + result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");

    }

    /*
     * AbstractOAuth2ApiBinding类里的2个属性：
     * accessToken：针对每一个用户走完OAuth流程后(前5步)都会为用户单独创建一个QQ API的实现，
     * 然后在里面存自个的accessToken
     * restTemplate：用来发http请求
     * */
    @Override
    public QQUserInfo getQQUserInfo() {

        // 这个写法就是把URL_GET_USERINFO的%s替换为appId和openId的值，上面的构造函数已经处理了access_token，这里就不用传了
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        logger.info("实例化QQApi后，获取用户信息为：" + result);

        QQUserInfo userInfo = null;
        try {
            // 把一个String读成一个我们想要的类型(QQUserInfo.class)
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
