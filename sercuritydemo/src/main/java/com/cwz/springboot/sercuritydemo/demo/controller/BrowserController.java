package com.cwz.springboot.sercuritydemo.demo.controller;

import com.cwz.springboot.sercuritydemo.demo.entity.Article;
import com.cwz.springboot.sercuritydemo.demo.entity.User;
import com.cwz.springboot.sercuritydemo.demo.repository.ArticleRepository;
import com.cwz.springboot.sercuritydemo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class BrowserController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @GetMapping("/signIn.html")
    public String signIn() {
        return "signIn";
    }

    @GetMapping("/browserSignIn.html")
    public String browserSignIn() {
        return "browserSignIn";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/cwz-signUp.html")
    public String cwzSignUp() {
        return "cwz-signUp";
    }

    @GetMapping("/standard-signUp.html")
    public String standardSignUp() {
        return "standard-signUp";
    }

    @GetMapping("/cwz-binding.html")
    public String cwzBanding() {
        return "cwz-binding";
    }

    @GetMapping("/standard-banding.html")
    public String standardBanding() {
        return "standard-banding";
    }

    @GetMapping("/cwz-logout.html")
    public String cwzLogout() {
        return "cwz-logout";
    }

    @PostMapping("/regist")
    public String regist(User user, HttpServletRequest request) {

        // 不管是注册用户还是绑定用户，都会拿到一个唯一标识，这里就用用户填的username作为用户的唯一标识
        // 这里把userId传给spring social然后于openId做验证进行绑定存入数据库中
        String userId = user.getUsername();

        // 这里要传一个request，因为他要从session里面存的Connection信息就是QQ里的信息拿出来，然后把这两个信息绑到一块插到数据库里
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));

        //userDetailsService.loadUserByUserId(userId);

        //request.setAttribute("password", "123456");

        return "redirect:/success";
    }

    @GetMapping("/article")
    @ResponseBody
    public List<Article> article() {
        return articleRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @GetMapping("/me1")
    @ResponseBody
    public Object getCurrentUser1(Authentication authentication) {
        //return SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    // 仅仅只拿"authorities"里的"principal"对象
    @GetMapping("/me2")
    @ResponseBody
    public Object getCurrentUser2(@AuthenticationPrincipal UserDetails userDetails) {
        //return SecurityContextHolder.getContext().getAuthentication();
        return userDetails;
    }

    @GetMapping("/user")
    @ResponseBody
    public List<User> user() {
        return userRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/getSession")
    public Authentication getSession(HttpServletRequest request) {
        SecurityContextImpl securityContextImpl =
                (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return securityContextImpl.getAuthentication();
    }

    @ResponseBody
    @GetMapping("/getUserId")
    public String getUserId(HttpServletRequest request) {
        SecurityContextImpl securityContextImpl =
                (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return ((UserDetails) securityContextImpl.getAuthentication().getPrincipal()).getUsername();
    }
}
