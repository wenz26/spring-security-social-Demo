package com.cwz.springboot.sercuritydemo.core.validate.code.Image;

import com.cwz.springboot.sercuritydemo.core.validate.code.Sms.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/*
 * 图形验证码API
 *
 * */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    private String code;

    // 验证码过期时间(LocalDateTime: 当前时间)
    private LocalDateTime expireTime;

    // 多少秒后会过期(精确到秒数)
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;

    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;

    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
