package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysLog;
import com.myproject.blog.biz.mapper.LogMapper;
import com.myproject.blog.biz.service.LogService;
import com.myproject.blog.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<SysLog> list(SysLog sysLog, QueryPage queryPage) {
        IPage<SysLog> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysLog.getUsername()), SysLog::getUsername, sysLog.getUsername());
        queryWrapper.orderByDesc(SysLog::getId);
        return logMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logMapper.deleteById(id);
    }
}
