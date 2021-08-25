package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysLink;
import com.myproject.blog.common.utils.QueryPage;

public interface LinkService extends IService<SysLink> {
    IPage<SysLink> list(SysLink sysLink, QueryPage queryPage);

    void add(SysLink sysLink);

    void update(SysLink sysLink);

    void delete(Long id);
}
