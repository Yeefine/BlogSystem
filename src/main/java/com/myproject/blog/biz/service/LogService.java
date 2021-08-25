package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysLog;
import com.myproject.blog.common.utils.QueryPage;

public interface LogService extends IService<SysLog> {
    IPage<SysLog> list(SysLog sysLog, QueryPage queryPage);

    void delete(Long id);
}
