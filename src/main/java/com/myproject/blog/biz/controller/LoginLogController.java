package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysLoginLog;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.LoginLogService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loginlog")
@Api(value = "LoginLogController", tags = {"登录日志管理接口"})
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping("/list")
    public R findByPage(@RequestBody SysLoginLog sysLoginLog, QueryPage queryPage, HttpServletRequest request) {
        SysUser currentUser = (SysUser) request.getSession().getAttribute("user");
        sysLoginLog.setUsername(currentUser.getUsername());
        return new R<>(super.getData(loginLogService.list(sysLoginLog, queryPage)));
    }

    @Log("删除登录日志")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        try {
            loginLogService.delete(id);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
