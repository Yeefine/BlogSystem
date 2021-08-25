package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.SysCategory;
import com.myproject.blog.biz.mapper.CategoryMapper;
import com.myproject.blog.biz.service.ArticleCategoryService;
import com.myproject.blog.biz.service.CategoryServie;
import com.myproject.blog.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServieImpl extends ServiceImpl<CategoryMapper, SysCategory> implements CategoryServie {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Override
    public List<SysCategory> findByArticleId(Long id) {
        return categoryMapper.findCategoryByArticleId(id);
    }

    @Override
    public List<SysCategory> list(SysCategory sysCategory) {
        LambdaQueryWrapper<SysCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysCategory.getName()), SysCategory::getName, sysCategory.getName());
        queryWrapper.orderByDesc(SysCategory::getId);
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<SysCategory> list(SysCategory sysCategory, QueryPage queryPage) {
        IPage<SysCategory> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysCategory.getName()), SysCategory::getName,sysCategory.getName());
        queryWrapper.orderByDesc(SysCategory::getId);
        return categoryMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(SysCategory sysCategory) {
        if (!exists(sysCategory)) {
            categoryMapper.insert(sysCategory);
        }
    }

    @Override
    @Transactional
    public void update(SysCategory sysCategory) {
        categoryMapper.updateById(sysCategory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryMapper.deleteById(id);
        // 更新 文章-目录表
        articleCategoryService.deleteByCategoryId(id);
    }

    private boolean exists(SysCategory sysCategory) {
        LambdaQueryWrapper<SysCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCategory::getName, sysCategory.getName());
        return categoryMapper.selectList(queryWrapper).size() > 0 ? true : false;
    }
}
