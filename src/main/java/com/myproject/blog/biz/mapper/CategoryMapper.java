package com.myproject.blog.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.blog.biz.entity.SysCategory;

import java.util.List;

public interface CategoryMapper extends BaseMapper<SysCategory> {
    List<SysCategory> findCategoryByArticleId(Long id);
}
