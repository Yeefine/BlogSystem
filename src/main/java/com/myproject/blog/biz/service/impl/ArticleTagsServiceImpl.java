package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysArticleTag;
import com.myproject.blog.biz.mapper.ArticleTagsMapper;
import com.myproject.blog.biz.service.ArticleTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleTagsServiceImpl extends ServiceImpl<ArticleTagsMapper, SysArticleTag> implements ArticleTagsService {

    @Autowired
    private ArticleTagsMapper articleTagsMapper;

    @Override
    @Transactional
    public void deleteByTagsId(Long id) {
        LambdaQueryWrapper<SysArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysArticleTag::getTagId, id);
        articleTagsMapper.delete(queryWrapper);
    }

    @Override
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<SysArticleTag> queryWrapper = new LambdaQueryWrapper<SysArticleTag>();
        queryWrapper.eq(SysArticleTag::getArticleId, id);
        articleTagsMapper.delete(queryWrapper);
    }

    @Override
    public void add(SysArticleTag sysArticleTag) {
        if (!exists(sysArticleTag)) {
            articleTagsMapper.insert(sysArticleTag);
        }
    }

    private boolean exists(SysArticleTag sysArticleTag) {
        LambdaQueryWrapper<SysArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysArticleTag::getArticleId, sysArticleTag.getArticleId());
        queryWrapper.eq(SysArticleTag::getTagId, sysArticleTag.getTagId());
        return articleTagsMapper.selectList(queryWrapper).size() > 0;
    }
}
