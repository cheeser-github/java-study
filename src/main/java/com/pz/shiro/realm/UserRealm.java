package com.pz.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pz.shiro.entity.Account;
import com.pz.shiro.mapper.AccountMapper;
import com.pz.shiro.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("username", (String)token.getPrincipal()));
        return new SimpleAuthenticationInfo(account,account.getPassword(),"pz");
    }



}
