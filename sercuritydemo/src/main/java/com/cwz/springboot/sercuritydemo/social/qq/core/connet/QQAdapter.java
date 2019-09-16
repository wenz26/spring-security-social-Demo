package com.cwz.springboot.sercuritydemo.social.qq.core.connet;

import com.cwz.springboot.sercuritydemo.social.qq.core.api.QQ;
import com.cwz.springboot.sercuritydemo.social.qq.core.entity.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/*
 * QQAdapter所实现的类ApiAdapter，要适配的是Api的接口类，也就是QQ
 * */
public class QQAdapter implements ApiAdapter<QQ> {

    // 用来测试当前的Api是否可用(一般情况下要去验证，这里就不验证，直接通过)
    @Override
    public boolean test(QQ api) {
        return true;
    }

    // 让Connection和Api里的数据做一个适配
    // ConnectionValues里包含了创建一个Connection所需要包含的数据
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {

        QQUserInfo userInfo = api.getQQUserInfo();

        // 显示出来用户的名字，这里用的是userInfo的昵称
        values.setDisplayName(userInfo.getNickname());
        // 用户的头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页，qq没有个人主页所以填一个null
        values.setProfileUrl(null);
        // 服务商的用户Id，也就是openId
        values.setProviderUserId(userInfo.getOpenId());
    }

    //
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    // 和 ProfileUrl 差不多，用于更新主页什么的
    @Override
    public void updateStatus(QQ api, String message) {
        // do noting
    }
}
