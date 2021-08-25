package com.myproject.blog.biz.controller.router;

import com.myproject.blog.biz.entity.SysArticle;
import com.myproject.blog.biz.service.ArticleService;
import com.myproject.blog.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 博客前台页面路由
 */
@ApiIgnore
@Controller
public class RouterController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index() {
        return "index/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about/index";
    }

    @GetMapping("/links")
    public String links() {
        return "link/index";
    }

    /**
     * 跳转文章详细信息页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/p/{id}")
    public String p(@PathVariable Long id, Model model) {
        SysArticle sysArticle = articleService.findById(id);
        if (sysArticle != null) {
            model.addAttribute("p", sysArticle);
        } else {
            return "error/404";
        }
        return "p/index";
    }
}
