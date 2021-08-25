package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysComment;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.CommentService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.constants.CommonConstant;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.AddressUtil;
import com.myproject.blog.common.utils.IPUtil;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 评论功能接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/chart")
    public R chart(HttpServletRequest request) {
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        return new R<>(commentService.chart(user));
    }

    @PostMapping("/list")
    public R list(@RequestBody SysComment sysComment, QueryPage queryPage) {
        return new R<>(this.getData(commentService.list(sysComment, queryPage)));
    }

    @DeleteMapping("/{id}")
    @Log("删除评论")
    public R delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/findByArticle/{id}")
    public R findByArticle(@PathVariable Long id) {
        return new R(commentService.findByArticle(id));
    }

    @PostMapping
    @Log("提交评论")
    public R save(@RequestBody SysComment sysComment, HttpServletRequest request) {
        try {
            String ip = IPUtil.getIpAddr(request);
            sysComment.setCreateTime(new Date());
            sysComment.setIp(ip);
            sysComment.setAddress(AddressUtil.getAddress(ip));
            String header = request.getHeader(CommonConstant.USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            sysComment.setDevice(browser.getName() + "," + operatingSystem.getName());
            commentService.add(sysComment);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
