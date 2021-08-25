package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysLoginLog;
import com.myproject.blog.biz.mapper.LoginLogMapper;
import com.myproject.blog.biz.service.LoginLogService;
import com.myproject.blog.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, SysLoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void saveLog(SysLoginLog log) {
        loginLogMapper.insert(log);
    }

    @Override
    public IPage<SysLoginLog> list(SysLoginLog sysLoginLog, QueryPage queryPage) {
        IPage<SysLoginLog> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysLoginLog.getUsername()), SysLoginLog::getUsername, sysLoginLog.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(sysLoginLog.getLocation()), SysLoginLog::getLocation, sysLoginLog.getLocation());
        queryWrapper.orderByDesc(SysLoginLog::getCreateTime);
        return loginLogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void delete(Long id) {
        loginLogMapper.deleteById(id);
    }
}
