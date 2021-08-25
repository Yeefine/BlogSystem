package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysTag;
import com.myproject.blog.common.utils.QueryPage;

import java.util.List;

public interface TagService extends IService<SysTag> {

    List<SysTag> findByArticleId(Long id);

    List<SysTag> list(SysTag sysTag);

    IPage<SysTag> list(SysTag sysTag, QueryPage queryPage);

    void add(SysTag sysTag);

    void update(SysTag sysTag);

    void delete(Long id);
}
