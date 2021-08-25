package com.myproject.blog.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.blog.biz.entity.SysArticleTag;

public interface ArticleTagsService extends IService<SysArticleTag> {
    void deleteByTagsId(Long id);

    void deleteByArticleId(Long id);

    void add(SysArticleTag sysArticleTag);
}
