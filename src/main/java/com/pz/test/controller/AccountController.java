package com.pz.test.controller;

import com.pz.test.service.AccountService;
import com.pz.test.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class AccountController {
    
    @Autowired
    AccountService accountService;
    
    @RequestMapping("/test")
    public void test(){
        Account byUsername = accountService.findByUsername("zs");
        System.out.println(byUsername);
    }
}

