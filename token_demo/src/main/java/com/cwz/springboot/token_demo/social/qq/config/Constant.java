package com.cwz.springboot.token_demo.social.qq.config;

public class Constant {

    // 获取QQ用户信息时的路径
    public static final String URL_GET_USERINFO =
            "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    // 通过accessToken去拿openId发的请求的路径
    public static final String URL_GET_OPENID =
            "https://graph.qq.com/oauth2.0/me?access_token=%s";

    // 将用户导向认证服务器的url，用户在这个地址上点确认进行授权
    public static final String URL_AUTHORIZE =
            "https://graph.qq.com/oauth2.0/authorize";

    // 通过授权码获取令牌，拿授权码申请令牌时的url
    public static final String URL_ACCESS_TOKEN =
            "https://graph.qq.com/oauth2.0/token";

}
