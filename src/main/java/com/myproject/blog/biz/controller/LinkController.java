package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysLink;
import com.myproject.blog.biz.service.LinkService;
import com.myproject.blog.common.annotation.Log;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link")
@Api(value = "LinkController", tags = {"友链管理接口"})
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;

    @PostMapping("list")
    public R list(@RequestBody SysLink sysLink, QueryPage queryPage) {

        return new R<>(super.getData(linkService.list(sysLink, queryPage)));
    }

    @PostMapping
    @Log("新增友链")
    public R save(@RequestBody SysLink sysLink) {
        try{
            linkService.add(sysLink);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R(linkService.getById(id));
    }

    @PutMapping
    @Log("更新友链")
    public R update(@RequestBody SysLink sysLink) {
        try {
            linkService.update(sysLink);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除友链")
    public R delete(@PathVariable Long id) {
        try {
            linkService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }


}
