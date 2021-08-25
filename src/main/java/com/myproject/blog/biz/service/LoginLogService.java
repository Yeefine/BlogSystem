package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysLoginLog;
import com.myproject.blog.common.utils.QueryPage;

public interface LoginLogService extends IService<SysLoginLog> {
    void saveLog(SysLoginLog log);

    IPage<SysLoginLog> list(SysLoginLog sysLoginLog, QueryPage queryPage);

    void delete(Long id);
}
