package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.ArticleService;
import com.myproject.blog.biz.service.UserService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户功能接口
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = {"用户功能接口"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/chart")
    public R chart() {
        return new R<>(userService.chart());
    }

    @GetMapping("/info")
    public R getInfo() {
        return new R<>(this.getCurrentUser());
    }

    @PutMapping
    @Log("更新用户")
    public R update(@RequestBody SysUser sysUser, HttpServletRequest request) {
        try {
//            SysUser currentUser = (SysUser) request.getSession().getAttribute("user");
//            String currentUser = this.getCurrentUser().getUsername();
            userService.update(sysUser);
//            articleService.updateAuthor(sysUser, currentUser);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/checkName")
    public R checkName(String userName) {
        return new R<>(userService.checkName(userName, this.getCurrentUser().getUsername()));
    }

    @PutMapping("/changePass")
    @Log("修改密码")
    public R updatePass(@RequestBody SysUser sysUser, HttpServletRequest request) {
        try{

//            System.out.println(sysUser.getPassword());
            SysUser currentUser = (SysUser) request.getSession().getAttribute("user");

            userService.updatePass(sysUser, currentUser);
            return new R<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }

    }
}
