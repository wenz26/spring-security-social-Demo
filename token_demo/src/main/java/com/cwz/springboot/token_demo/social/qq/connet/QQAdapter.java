package com.cwz.springboot.token_demo.social.qq.connet;

import com.cwz.springboot.token_demo.social.qq.Info.QQUserInfo;
import com.cwz.springboot.token_demo.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

// QQAdapter所实现的类ApiAdapter，要适配的是Api的接口类，也就是QQ
public class QQAdapter implements ApiAdapter<QQ> {

    // 用来测试当前的Api是否可用(一般情况下要去验证，这里就不验证，直接通过)
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    // 让Connection和Api里的数据做一个适配
    // ConnectionValues里包含了创建一个Connection所需要包含的数据
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getQQUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return UserProfile.EMPTY;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
