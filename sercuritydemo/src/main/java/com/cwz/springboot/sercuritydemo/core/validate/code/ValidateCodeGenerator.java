package com.cwz.springboot.sercuritydemo.core.validate.code;

import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    public ValidateCode createCode(ServletWebRequest request);
}
