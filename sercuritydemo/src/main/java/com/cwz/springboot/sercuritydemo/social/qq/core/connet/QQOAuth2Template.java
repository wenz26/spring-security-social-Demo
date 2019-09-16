package com.cwz.springboot.sercuritydemo.social.qq.core.connet;

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


    public QQOAuth2Template(String clientId, String clientSecret,
                            String authorizeUrl, String accessTokenUrl) {

        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {

        RestTemplate restTemplate = super.createRestTemplate();

        // 这个再RestTemplate里添加一个Converter(转换器)，这个Converter将能替我们处理template的html的这种template
        restTemplate.getMessageConverters()
                .add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }

    /*
     * 这个方法处理QQ获取授权码后再去获取获取Access_Token时返回的不是json数据
     * QQ的返回数据为：http://graph.qq.com/demo/index.jsp?code=9A5F************************06AF&state=test
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应：" + responseStr);

        // 把返回的数据进行切割，不忽略空格，空格也算在里面
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        // 拿第1个切割的数组元素等号后面的值
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        // 拿第2个切割的数组元素等号后面的值(这是个Long类型的值)
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        // 拿第3个切割的数组元素等号后面的值
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        // 把QQ请求格式的标准做了一个自定义的解析
        return new AccessGrant(accessToken, "all", refreshToken, expiresIn);
    }


}
