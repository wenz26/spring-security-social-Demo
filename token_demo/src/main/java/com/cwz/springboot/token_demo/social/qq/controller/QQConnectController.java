package com.cwz.springboot.token_demo.social.qq.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 *  调用ConnectController查看绑定
*/
@Component
@RequestMapping("/connect")
public class QQConnectController extends ConnectController implements InitializingBean {

    public QQConnectController(ConnectionFactoryLocator connectionFactoryLocator,
                               ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);

    }



}
