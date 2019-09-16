package com.cwz.springboot.token_demo.social.qq.connet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    QQOAuth2Template(String clientId, String clientSecret,
                            String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {

        RestTemplate restTemplate = super.createRestTemplate();

        restTemplate.getMessageConverters()
                .add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应为：" + responseStr);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        // 拿第1个切割的数组元素等号后面的值
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        // 拿第2个切割的数组元素等号后面的值(这是个Long类型的值)
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        // 拿第3个切割的数组元素等号后面的值
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        // 把QQ请求格式的标准做了一个自定义的解析
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }
}
