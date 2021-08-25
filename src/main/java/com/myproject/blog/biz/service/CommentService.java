package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysComment;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.common.utils.QueryPage;

import java.util.List;

public interface CommentService extends IService<SysComment> {

    int countByUser(SysUser user);

    List<Long[]> chart(SysUser user);

    IPage<SysComment> list(SysComment sysComment, QueryPage queryPage);

    void delete(Long id);

    List<SysComment> findByArticle(Long id);

    void add(SysComment sysComment);
}
