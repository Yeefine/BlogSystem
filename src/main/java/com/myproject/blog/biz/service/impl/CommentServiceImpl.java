package com.myproject.blog.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysComment;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.mapper.CommentMapper;
import com.myproject.blog.biz.service.CommentService;
import com.myproject.blog.common.utils.QueryPage;
import com.myproject.blog.common.utils.SplineChart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, SysComment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int countByUser(SysUser user) {
        int count = commentMapper.countByUser(user.getUsername());
        return count;
    }

    @Override
    public List<Long[]> chart(SysUser user) {
        List<Long[]> splineChart = new ArrayList<>();
        List<SplineChart> splineChartList = commentMapper.chart(user);
        if (splineChartList.size() > 0) {
            splineChartList.forEach(item -> {
                Long[] d = {DateUtil.parse(item.getTime(), "yyyy-MM-dd").getTime(), item.getNum()};
                splineChart.add(d);
            });
        }
        return splineChart;
    }

    @Override
    public IPage<SysComment> list(SysComment sysComment, QueryPage queryPage) {
        IPage<SysComment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sysComment.getContent()), SysComment::getContent, sysComment.getContent());
        queryWrapper.orderByDesc(SysComment::getId);
        return commentMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public List<SysComment> findByArticle(Long id) {
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysComment::getArticleId, id);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysComment sysComment) {
        commentMapper.insert(sysComment);
    }
}
