package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysTag;
import com.myproject.blog.biz.mapper.TagMapper;
import com.myproject.blog.biz.service.ArticleTagsService;
import com.myproject.blog.biz.service.TagService;
import com.myproject.blog.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, SysTag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagsService articleTagsService;

    @Override
    public List<SysTag> findByArticleId(Long id) {
        return tagMapper.findTagsByArticleId(id);
    }

    @Override
    public List<SysTag> list(SysTag sysTag) {
        LambdaQueryWrapper<SysTag> queryWrapper = new LambdaQueryWrapper<SysTag>();
        queryWrapper.like(StringUtils.isNotBlank(sysTag.getName()), SysTag::getName, sysTag.getName());
        queryWrapper.orderByDesc(SysTag::getId);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<SysTag> list(SysTag sysTag, QueryPage queryPage) {
        IPage<SysTag> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysTag> queryWrapper = new LambdaQueryWrapper<SysTag>();
        queryWrapper.like(StringUtils.isNotBlank(sysTag.getName()), SysTag::getName, sysTag.getName());
        queryWrapper.orderByDesc(SysTag::getId);
        return tagMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void add(SysTag sysTag) {
        if (!exists(sysTag)) {
            tagMapper.insert(sysTag);
        }
    }

    @Override
    @Transactional
    public void update(SysTag sysTag) {
        tagMapper.updateById(sysTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagMapper.deleteById(id);

        // 删除该标签与文章有关联的关联信息
        articleTagsService.deleteByTagsId(id);
    }

    private boolean exists(SysTag sysTag) {
        LambdaQueryWrapper<SysTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTag::getName, sysTag.getName());
        return tagMapper.selectList(queryWrapper).size() > 0;
    }
}
