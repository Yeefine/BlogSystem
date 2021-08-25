package com.myproject.blog.biz.controller.router;


import com.myproject.blog.biz.entity.SysLoginLog;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.LoginLogService;
import com.myproject.blog.biz.service.UserService;
import com.myproject.blog.common.constants.CommonConstant;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.*;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api(value = "LoginController", tags = {"登录接口"})    // 表明是swagger资源
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private Md5Util md5Util;

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 登录接口
     */

    @PostMapping("/login")
    public R login(@RequestParam(value = "username", required = false) String username,
                   @RequestParam(value = "password", required = false) String password) {
        Subject subject = getSubject();
        String encrypt_password = md5Util.encryptPassword(password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, encrypt_password);

        try {
            subject.login(token);
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            // 记录登录日志
            SysLoginLog log = new SysLoginLog();
            // 获取HTTP请求
            String ip = IPUtil.getIpAddr(request);
            log.setIp(ip);
            log.setUsername(super.getCurrentUser().getUsername());
            log.setLocation(AddressUtil.getAddress(ip));
            log.setCreateTime(new Date());
            // 获取单个请求头信息
            String header = request.getHeader(CommonConstant.USER_AGENT);
            // 转成UserAgent对象
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            // 获取浏览器信息
            Browser browser = userAgent.getBrowser();
            // 获取系统信息
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            log.setDevice(browser.getName() + " -- " + operatingSystem.getName());
            loginLogService.saveLog(log);

            request.getSession().setAttribute("user", this.getCurrentUser());
            return new R<>();

        } catch (Exception e) {
            e.printStackTrace();
            return new R<>();
        }

    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public R save(@RequestBody SysUser sysUser) {   // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的
        try {
            userService.add(sysUser);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }

    }

}
