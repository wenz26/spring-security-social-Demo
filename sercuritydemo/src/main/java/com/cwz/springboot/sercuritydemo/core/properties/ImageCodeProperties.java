package com.cwz.springboot.sercuritydemo.core.properties;

public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        //setLength(4);
    }

    private int width = 67;

    private int heigth = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

}
