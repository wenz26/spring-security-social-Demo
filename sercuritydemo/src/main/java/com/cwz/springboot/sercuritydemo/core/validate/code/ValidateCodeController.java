package com.cwz.springboot.sercuritydemo.core.validate.code;

import com.cwz.springboot.sercuritydemo.core.validate.code.Image.ImageCode;
import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.SmsCodeSender;
import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
 *  创建验证码，根据验证码类型不同，调用不同的接口实现
 *
 * */
@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    public static final String SESSION_KEY_PREFIX_SMS = "SESSION_KEY_PREFIX_SMS";

    // session策略
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    // 图片验证码
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // new ServletWebRequest(request)为给定的请求创建一个新的ServletWebRequest实例。
        ImageCode imageCode = (ImageCode) imageCodeGenerator.createCode(new ServletWebRequest(request));
        // redis里不能存图片 就只存图片的验证码和过期时间
        ValidateCode code = new ValidateCode(imageCode.getCode(), imageCode.getExpireTime());

        // 把该请求加到session里，第一个参数是把这个request请求加到session里，第二个参数是session的key, 第三个参数是session的value
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, code);

        // 在将生成的图片写到接口的响应中
        // response.getOutputStream() 响应的输出流
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());

    }

    // 短信验证
    @GetMapping("/code/sms/{mobile}")
    public String createSmsCode(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("mobile") String mobile) {

        // 验证
        System.out.println(smsCodeGenerator + "  " + imageCodeGenerator);
        System.out.println(imageCodeGenerator == smsCodeGenerator);


        // new ServletWebRequest(request)为给定的请求创建一个新的ServletWebRequest实例。
        ValidateCode smsCode = smsCodeGenerator.createCode(new ServletWebRequest(request));

        // 把该请求加到session里，第一个参数是把这个request请求加到session里，第二个参数是session的key, 第三个参数是session的value
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_PREFIX_SMS, smsCode);

        // 从请求里拿手机号
        //String mobile = ServletRequestUtils.getStringParameter(request, "mobile");

        // 连接短信服务商 发送短信 (这里不连，而是直接打印在控制台上)
        smsCodeSender.send(mobile, smsCode.getCode());

        return smsCode.getCode();
    }

}
