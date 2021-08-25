package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysArticle;
import com.myproject.blog.biz.service.ArticleService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(value = "ArticleController", tags = {"文章功能接口"})
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/list")
    public R list(@RequestBody SysArticle sysArticle, QueryPage queryPage) {
        if (this.getCurrentUser() != null) {
            String author = this.getCurrentUser().getUsername();
            sysArticle.setAuthor(author);
        }

        return new R<>(super.getData(articleService.list(sysArticle, queryPage)));
    }

    @PostMapping
    @Log("新增文章")
    public R add(@RequestBody SysArticle sysArticle) {
        try{
            sysArticle.setAuthor(this.getCurrentUser().getUsername());
            articleService.add(sysArticle);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public R findById(@PathVariable  Long id) {
        return new R<>(articleService.findById(id));
    }

    @DeleteMapping("{id}")
    @Log("删除文章")
    public R delete(@PathVariable Long id) {
        try {
            articleService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新文章")
    public R update(@RequestBody SysArticle sysArticle) {
        try {
            sysArticle.setAuthor(this.getCurrentUser().getUsername());
            articleService.update(sysArticle);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/findByTag/{id}")
    public R findByTag(@PathVariable Long id) {

        return new R<>(articleService.findByTag(id, this.getCurrentUser().getUsername()));
    }

    @GetMapping("/findByCategory/{id}")
    public R findByCategory(@PathVariable Long id) {
        String userName = this.getCurrentUser().getUsername();
        return new R(articleService.findByCategory(id, userName));
    }


}
