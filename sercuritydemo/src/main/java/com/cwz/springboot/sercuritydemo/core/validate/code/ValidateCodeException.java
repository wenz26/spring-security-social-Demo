package com.cwz.springboot.sercuritydemo.core.validate.code;


import org.springframework.security.core.AuthenticationException;

/*
 * 这个是针对验证码的异常处理
 *
 * AuthenticationException: 是security用例的一个抽象的异常，它是所有的身份验证过程中抛出异常的一个基类
 * */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
