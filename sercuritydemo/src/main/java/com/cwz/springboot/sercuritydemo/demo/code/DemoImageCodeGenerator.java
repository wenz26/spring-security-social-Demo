package com.cwz.springboot.sercuritydemo.demo.code;

import com.cwz.springboot.sercuritydemo.core.validate.code.Image.ImageCode;
import com.cwz.springboot.sercuritydemo.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode createCode(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成器");
        return null;
    }
}
