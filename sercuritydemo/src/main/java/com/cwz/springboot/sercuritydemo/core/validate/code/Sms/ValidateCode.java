package com.cwz.springboot.sercuritydemo.core.validate.code.Sms;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * 验证码API
 * 验证码的一个基类，图片验证码要继承这个类
 * */
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 42L;

    private String code;

    // 验证码过期时间(LocalDateTime: 当前时间)
    private LocalDateTime expireTime;

    // 多少秒后会过期(精确到秒数)
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    // 是否过期
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
