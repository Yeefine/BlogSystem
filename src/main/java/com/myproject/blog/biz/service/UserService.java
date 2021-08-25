package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysUser;

import java.util.List;

public interface UserService extends IService<SysUser> {


    SysUser findByName(String username);

    void add(SysUser sysUser);

    List<Long[]> chart();

    void update(SysUser sysUser);

    SysUser checkName(String userName, String currentName);

    void updatePass(SysUser user, SysUser currentUser);
}
