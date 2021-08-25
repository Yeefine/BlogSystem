package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysTag;
import com.myproject.blog.biz.service.TagService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@Api(value = "TagController", tags = {"标签管理接口"})
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @PostMapping("/list")
    public R findByPage(@RequestBody SysTag sysTag, QueryPage queryPage) {
        return new R<>(super.getData(tagService.list(sysTag, queryPage)));
    }

    @PostMapping("/filter/list")
    public R list(@RequestBody SysTag sysTag) {
        return new R(tagService.list(sysTag));
    }

    @PostMapping
    @Log("新增标签")
    public R save(@RequestBody SysTag sysTag) {
        try {
            tagService.add(sysTag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R(tagService.getById(id));
    }

    @PutMapping
    @Log("更新标签")
    public R update(@RequestBody SysTag sysTag) {
        try {
            tagService.update(sysTag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除标签")
    public R delete(@PathVariable Long id) {
        try {
            tagService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
