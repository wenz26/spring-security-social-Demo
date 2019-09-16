/**
 *
 */
package com.cwz.springboot.sercuritydemo.social.weixin.core.config;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.social.config.SocialAutoConfigurerAdapter;
import com.cwz.springboot.sercuritydemo.social.weixin.core.connet.WeixinConnectionFactory;
import com.cwz.springboot.sercuritydemo.social.weixin.core.properties.WeixinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "com.cwz.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
     * #createConnectionFactory()
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

	/*@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ImoocConnectView();
	}*/

}
