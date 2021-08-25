package com.myproject.blog.biz.controller;

import com.myproject.blog.biz.entity.SysLog;
import com.myproject.blog.biz.service.LogService;
import com.myproject.blog.common.controller.BaseController;
import com.myproject.blog.common.exception.GlobalException;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@Api(value = "LogController", tags = {"日志管理接口"})
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @PostMapping("/list")
    public R list(@RequestBody SysLog sysLog, QueryPage queryPage) {
        return new R<>(super.getData(logService.list(sysLog, queryPage)));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        try {
            logService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
