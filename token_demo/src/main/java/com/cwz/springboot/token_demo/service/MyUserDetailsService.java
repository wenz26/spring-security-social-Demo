package com.cwz.springboot.token_demo.service;

import com.cwz.springboot.token_demo.jpa.entity.User;
import com.cwz.springboot.token_demo.social.qq.helper.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("登录表单的用户名为：" + username);

        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        logger.info("登录社交账号的userId为：" + userId);

        return buildUser(userId);

        /*return new SocialUser(userId, passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER"));*/
    }

    private SocialUserDetails buildUser(String username){

        /*HttpServletRequest request = HttpHelper.getRequest();
        String password = (String) request.getAttribute("password");*/

        User user = userService.findUserByUsername(username);

        if (user != null){

            logger.info("登录成功！！！");

            return new SocialUser(username, user.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER"));
        } else {

            logger.info("登录失败，用户名或密码错误！！！");

            throw new UsernameNotFoundException("用户名或密码错误！！！");
        }
    }
}
