package com.pz.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pz.shiro.entity.Account;
import com.pz.shiro.mapper.AccountMapper;
import com.pz.shiro.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
