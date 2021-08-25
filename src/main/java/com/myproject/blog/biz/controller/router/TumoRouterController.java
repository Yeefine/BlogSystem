package com.myproject.blog.biz.controller.router;

import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.ArticleService;
import com.myproject.blog.biz.service.CommentService;
import com.myproject.blog.biz.service.TagService;
import com.myproject.blog.biz.service.UserService;
import com.myproject.blog.common.controller.BaseController;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 博客后台页面路由
 */
@ApiIgnore
@Controller
public class TumoRouterController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    /**
     * 注销接口
     */
    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject = getSubject();
        subject.logout();
        return "redirect:/login";
    }

    /**
     * 登录状态验证
     * @param request
     * @param model
     * @return
     */
    private boolean auth(HttpServletRequest request, Model model) {
        Object user = request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return user instanceof SysUser;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "register";
    }


    @GetMapping("/tumo")
    public String index(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        SysUser user = (SysUser) model.getAttribute("user");
        int articleCount = articleService.countByUser(user);
        int commentCount = commentService.countByUser(user);
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("tagCount", tagService.count());
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("userCount", userService.count());
        return "tumo/index/index";
    }

    /**
     * 跳转账户设置
     */
    @GetMapping("/tumo/profile")
    public String profile(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:login";
        }
        return "tumo/profile/index";
    }

    @GetMapping("/tumo/article/write")
    public String articleWrite(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }

        return "/tumo/article/write/index";
    }

    @GetMapping("/tumo/article/list")
    public String articleList(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:login";
        }

        return "/tumo/article/list/index";
    }

    @GetMapping("/tumo/blog/tag")
    public String blogTag(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/blog/tag/index";
    }

    @GetMapping("/tumo/blog/category")
    public String blogCategory(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/blog/category/index";
    }

    @GetMapping("/tumo/blog/link")
    public String blogLink(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/blog/link/index";
    }

    @GetMapping("/tumo/blog/comment")
    public String blogComment(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/blog/comment/index";
    }

    @GetMapping("/tumo/setting/log")
    public String settingLog(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/setting/log/index";
    }

    @GetMapping("/tumo/setting/qiniu")
    public String settingQiniu(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/setting/qiniu/index";
    }

    @GetMapping("/tumo/setting/swagger")
    public String settingSwagger(HttpServletRequest request, Model model) {
        if (!this.auth(request, model)) {
            return "redirect:/login";
        }
        return "tumo/setting/swagger/index";
    }




}
