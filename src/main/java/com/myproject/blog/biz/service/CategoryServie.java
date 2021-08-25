package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysCategory;
import com.myproject.blog.common.utils.QueryPage;

import java.util.List;

public interface CategoryServie extends IService<SysCategory> {

    // 根据文章id查询其关联的分类信息
    List<SysCategory> findByArticleId(Long id);

    List<SysCategory> list(SysCategory sysCategory);

    IPage<SysCategory> list(SysCategory sysCategory, QueryPage queryPage);

    void add(SysCategory sysCategory);

    void update(SysCategory sysCategory);

    void delete(Long id);
}
