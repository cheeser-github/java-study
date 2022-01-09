package com.pz.test.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pz.test.dao.AccountMapper;
import com.pz.test.entity.Account;
import com.pz.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        return accountMapper.selectOne(wrapper);
    }
}
