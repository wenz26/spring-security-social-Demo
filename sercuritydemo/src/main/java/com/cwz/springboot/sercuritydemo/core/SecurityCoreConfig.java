package com.cwz.springboot.sercuritydemo.core;

import com.cwz.springboot.sercuritydemo.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

// 该配置是让SecurityProperties这个读取器生效(或者可以再SecurityProperties这个类里加@Component,把他加到容器中)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {


    // 定义一个session 的 propertySourcesPlaceholderConfigurer


}
