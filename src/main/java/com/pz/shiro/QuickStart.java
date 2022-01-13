package com.pz.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

@Slf4j
public class QuickStart {
    public static void main(String[] args) {
        //1.加载shiro工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityManager manager = factory.getInstance();

        //2.获取subject
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        //3.判断改subject是否登录
        if (subject.isAuthenticated()){
            log.info("subect已经登录");
        }else {
            log.info("subject还未登录");
        }



        //4.subjcet登录
        UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
        token.setRememberMe(true);
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.info("There is no user with username of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            log.info("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
        }
        if (subject.isAuthenticated()) {
            log.info("{}已经登录",subject.getPrincipals());
        }

        //5.看是否拥有角色
        if (subject.hasRole("admin")) {
            log.info("{}有这个角色",subject.getPrincipals() );
        }




        //6.是否拥有权限
        if (subject.isPermitted("read")){
            log.info("{}有这个权限",subject.getPrincipal());
        }

    }
}
