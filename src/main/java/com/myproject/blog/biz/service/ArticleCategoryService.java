package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysArticleCategory;

public interface ArticleCategoryService extends IService<SysArticleCategory> {

    void deleteByArticleId(Long id);

    void add(SysArticleCategory sysArticleCategory);

    void deleteByCategoryId(Long id);
}
