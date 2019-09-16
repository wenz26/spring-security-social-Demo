package com.cwz.springboot.sercuritydemo.browser.support;

/*
 * 对返回的字符串做一个包装
 * */
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
