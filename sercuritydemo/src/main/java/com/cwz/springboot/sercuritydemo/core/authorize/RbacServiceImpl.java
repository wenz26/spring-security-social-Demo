package com.cwz.springboot.sercuritydemo.core.authorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;

        // instanceof: 它的作用是判断其左边对象是否为其右边类的实例，返回boolean类型的数据。
        // 只有当principal是一个UserDetails时才执行
        if (principal instanceof UserDetails) {

            String username = ((UserDetails) principal).getUsername();

            // 读取用户所拥有权限的所有url
            Set<String> urls = new HashSet<>();

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }

        return hasPermission;
    }
}
