package com.myproject.blog.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.blog.biz.entity.SysTag;

import java.util.List;

public interface TagMapper extends BaseMapper<SysTag> {
    List<SysTag> findTagsByArticleId(Long id);
}
