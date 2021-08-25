package com.myproject.blog.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.blog.biz.entity.SysArticle;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper extends BaseMapper<SysArticle> {
    @Update("update tb_article set author=#{userName} where author=#{currentName}")
    void updateAuthor(String userName, String currentName);

    List<SysArticle> findByTag(Long id, String userName);

    List<SysArticle> findByCategory(Long id, String userName);
}
