package com.cwz.springboot.sercuritydemo.core.validate.code;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import com.cwz.springboot.sercuritydemo.core.validate.code.Image.ImageCodeGenerator;
import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.DefaultSmsCodeSender;
import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean

    /*
     * 判断spring容器里是不是有一个名为imageCodeGenerator的bean，如果有就用找到的，
     * 如果没有就执行以下的配置
     * */
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean

    /*
     * 判断spring容器里是不是有一个名为smsCodeSender的bean，如果有就用找到的，
     * 如果没有就执行以下的配置
     * */
    @ConditionalOnMissingBean(name = "smsCodeSender")
    // 或者这么写也行@ConditionalOnMissingBean(smsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
