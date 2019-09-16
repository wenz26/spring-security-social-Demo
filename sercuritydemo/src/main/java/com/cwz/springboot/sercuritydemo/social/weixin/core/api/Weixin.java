/**
 *
 */
package com.cwz.springboot.sercuritydemo.social.weixin.core.api;

import com.cwz.springboot.sercuritydemo.social.weixin.core.entity.WeixinUserInfo;

/**
 * 微信API调用接口
 *
 * @author zhailiang
 *
 */
public interface Weixin {

    /* (non-Javadoc)
     * @see com.ymt.pz365.framework.security.social.api.SocialUserProfileService#getUserProfile(java.lang.String)
     */
    public WeixinUserInfo getUserInfo(String openId);

}
