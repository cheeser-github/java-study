package com.pz.shiro.controller;

import com.pz.shiro.entity.Account;
import com.pz.shiro.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    AccountService accountService;


    /**
     * 对于只输入localhost:端口号的url 直接跳转到登录页面
     */
//    @GetMapping("/")
//    public String login(){
//        Subject loginUser = SecurityUtils.getSubject();
//        return "login";
//    }

    @GetMapping("/{page}")
    public String toPage(@PathVariable String page){
            Subject loginUser = SecurityUtils.getSubject();
        return page;
    }

    /**
     * 执行用户登录
     */
    @ResponseBody
    @PostMapping("/login")
    public String loginAction(String username, String password ){  //post请求用户名密码放请求体里这样也是可以拿到的
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(new UsernamePasswordToken(username, password));
        currentUser.getSession().setAttribute("account",currentUser.getPrincipal());  //Principal再realm中存入的
        return "token-abcdefg";
    }


    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "login";
    }


}
