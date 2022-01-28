package com.pz.shiro.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("account")
public class Account {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String role;
}
