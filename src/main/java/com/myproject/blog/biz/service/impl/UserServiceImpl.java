package com.myproject.blog.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.mapper.UserMapper;
import com.myproject.blog.biz.service.UserService;
import com.myproject.blog.common.utils.Md5Util;
import com.myproject.blog.common.utils.SplineChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Md5Util md5Util;

    @Override
    public SysUser findByName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();  // mybatis plus条件构造器
        queryWrapper.eq(SysUser::getUsername, username);
        List<SysUser> list = userMapper.selectList(queryWrapper);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    @Transactional
    public void add(SysUser sysUser) {
        String encryptPassword = md5Util.encryptPassword(sysUser.getPassword());
        sysUser.setPassword(encryptPassword);
        sysUser.setAvatar("/img/avatar/default.jpg");
        sysUser.setCreateTime(new Date());
        userMapper.insert(sysUser);
    }

    @Override
    public List<Long[]> chart() {
        List<Long[]> splineChart = new ArrayList<>();
        List<SplineChart> splineChartList = userMapper.chart();
        if (splineChartList.size() > 0) {
            splineChartList.forEach(item -> {
                if (item.getTime() != null) {
                    Long[] d = {DateUtil.parse(item.getTime(), "yyyy-MM-dd").getTime(), item.getNum()};
                    splineChart.add(d);
                }
            });
        }
        return splineChart;
    }

    @Override
    public void update(SysUser sysUser) {
        sysUser.setPassword(null);
        userMapper.updateById(sysUser);
    }

    /**
     * 查询用户名等于userName，且不等于currentName的用户
     * @param userName
     * @param currentName
     * @return
     */
    @Override
    public SysUser checkName(String userName, String currentName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        queryWrapper.ne(SysUser::getUsername, currentName);
        List<SysUser> list = userMapper.selectList(queryWrapper);
        return list.size() > 0 ? list.get(0) : null;
    }


    @Override
    @Transactional
    public void updatePass(SysUser user, SysUser currentUser) {

        user.setId(currentUser.getId());

        String encryptPassword =  md5Util.encryptPassword(user.getPassword());
        user.setPassword(encryptPassword);
        userMapper.updateById(user);
    }
}
