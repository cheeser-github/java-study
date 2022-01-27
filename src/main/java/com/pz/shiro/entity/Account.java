package com.pz.shiro.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String role;
}
