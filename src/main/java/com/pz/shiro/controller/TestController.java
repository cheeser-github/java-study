package com.pz.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


@RestController
public class TestController {

//    @RequiresAuthentication
    @GetMapping("test1")
    @ResponseBody
    public String test1(){
        System.out.println("run function test1");
        return "aa";
    }

    @RequiresPermissions("write")
    @RequestMapping("/write")
    public void write(){
        System.out.println("进入写");
    }

    @RequiresPermissions("update")
    @RequestMapping("/update")
    public void update(){
        System.out.println("进入更新");
    }


}
