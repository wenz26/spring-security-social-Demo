package com.cwz.springboot.sercuritydemo.core.validate.code.Sms;


/*
 * 模拟短信验证码发送
 * */
public interface SmsCodeSender {

    public void send(String mobile, String code);
}
