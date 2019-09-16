package com.cwz.springboot.sercuritydemo.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    private Logger logger = LoggerFactory.getLogger(getClass());


    // 表单登录用的，这里通过传入的username 查找对应的userDetails，找到后存在session里
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("表单登录用户名：" + username);
        logger.info("passwordEncoder的密码加密器为：" + passwordEncoder.getClass());

        return buildUser(username);
    }


    /* 这里是社交登录用的，传进来的是spring social根据社交网站的用户的openId(QQ号码)查出来的userId，
     * 这里要做的是根据userId去构建一个UserDetails的实现再返回回去
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        logger.info("社交登录用户Id：" + userId);


        return buildUser(userId);


    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        // 根据查找到的用户信息判断用户是否被冻结

        // 密码加密
        String password = passwordEncoder.encode("123456");

        // User(username, 密码, 可用, 没过期, 密码没过期, 没被锁定, 集合(用户的权限集合))
        return new SocialUser(userId, password, true, true,
                true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER"));
    }
}
