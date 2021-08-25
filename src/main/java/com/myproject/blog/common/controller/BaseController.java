package com.myproject.blog.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myproject.blog.biz.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;


/*
    Controller层公共方法提取
 */
public class BaseController {

    // 获取当前登录用户的信息
    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    // 获取当前系统用户
    protected SysUser getCurrentUser() {
        return (SysUser) getSubject().getPrincipal();   // Principal中存放SysUser
    }

    public Map<String, Object> getData(IPage<?> page) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }

}
