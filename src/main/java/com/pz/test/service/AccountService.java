package com.pz.test.service;


import com.pz.test.entity.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
