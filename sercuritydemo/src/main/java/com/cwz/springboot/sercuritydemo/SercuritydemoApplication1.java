package com.cwz.springboot.sercuritydemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;


@SpringBootApplication

/* 主要是添加@EnableRedisHttpSession注解即可，该注解会创建一个名字叫springSessionRepositoryFilter
 *  的Spring Bean，其实就是一个Filter，这个Filter负责用Spring Session来替换原先的默认HttpSession实现
 * */
@EnableSpringHttpSession
public class SercuritydemoApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(SercuritydemoApplication.class, args);
    }
}
