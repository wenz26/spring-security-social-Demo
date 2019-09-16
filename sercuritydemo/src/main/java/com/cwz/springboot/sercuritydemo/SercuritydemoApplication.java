package com.cwz.springboot.sercuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.social.config.annotation.EnableSocial;


@SpringBootApplication

/* 主要是添加@EnableRedisHttpSession注解即可，该注解会创建一个名字叫springSessionRepositoryFilter
 *  的Spring Bean，其实就是一个Filter，这个Filter负责用Spring Session来替换原先的默认HttpSession实现
 *  @EnableRedisHttpSession注解参数maxInactiveIntervalInSeconds来设定超时时间
 * */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
@EnableSocial
public class SercuritydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SercuritydemoApplication.class, args);
    }

}
