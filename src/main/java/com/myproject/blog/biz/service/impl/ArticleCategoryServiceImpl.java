package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysArticleCategory;
import com.myproject.blog.biz.mapper.ArticleCategoryMapper;
import com.myproject.blog.biz.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, SysArticleCategory> implements ArticleCategoryService {


    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<SysArticleCategory> queryWrapper = new LambdaQueryWrapper<SysArticleCategory>();
        queryWrapper.eq(SysArticleCategory::getArticleId, id);
        articleCategoryMapper.delete(queryWrapper);
    }

    @Override
    public void add(SysArticleCategory sysArticleCategory) {
        if (!exists(sysArticleCategory)) {
            articleCategoryMapper.insert(sysArticleCategory);
        }
    }

    @Override
    public void deleteByCategoryId(Long id) {
        LambdaQueryWrapper<SysArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysArticleCategory::getCategoryId, id);
        articleCategoryMapper.delete(queryWrapper);
    }

    private boolean exists(SysArticleCategory sysArticleCategory) {
        LambdaQueryWrapper<SysArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysArticleCategory::getArticleId, sysArticleCategory.getArticleId());
        queryWrapper.eq(SysArticleCategory::getCategoryId, sysArticleCategory.getCategoryId());
        return articleCategoryMapper.selectList(queryWrapper).size() > 0;
    }
}
