package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysArticle;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.common.utils.QueryPage;

import java.util.List;

public interface ArticleService extends IService<SysArticle> {


    int countByUser(SysUser user);

    IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage);

    SysArticle findById(Long id);


    void updateAuthor(SysUser sysUser, String currentName);

    void add(SysArticle sysArticle);

    void delete(Long id);

    void update(SysArticle sysArticle);

    List<SysArticle> findByTag(Long id, String userName);

    List<SysArticle> findByCategory(Long id, String userName);
}
