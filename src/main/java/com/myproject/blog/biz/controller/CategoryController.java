package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysCategory;
import com.myproject.blog.biz.service.CategoryServie;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 分类功能接口
 */
@RestController
@RequestMapping("/category")
@Api(value = "CategoryController", tags = {"分类功能接口"})
public class CategoryController extends BaseController {

    @Autowired
    private CategoryServie categoryServie;

    @PostMapping("/filter/list")
    public R list(@RequestBody SysCategory sysCategory) {
        return new R<>(categoryServie.list(sysCategory));
    }

    @PostMapping("/list")
    public R<Map<String, Object>> list(@RequestBody SysCategory sysCategory, QueryPage queryPage) {
        return new R<>(super.getData(categoryServie.list(sysCategory, queryPage)));
    }

    @PostMapping
    @Log("新增分类")
    public R save(@RequestBody SysCategory sysCategory) {
        try {
            categoryServie.add(sysCategory);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新分类")
    public R update(@RequestBody SysCategory sysCategory) {
        try {
            categoryServie.update(sysCategory);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R(categoryServie.getById(id));
    }

    @DeleteMapping("/{id}")
    @Log("删除分类")
    public R delete(@PathVariable Long id) {
        try {
            categoryServie.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
