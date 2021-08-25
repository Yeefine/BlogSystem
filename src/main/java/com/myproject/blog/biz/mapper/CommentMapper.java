package com.myproject.blog.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.blog.biz.entity.SysComment;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.common.utils.SplineChart;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<SysComment> {

    @Select("select count(*) from tb_comment c join tb_article a on c.article_id=a.id where a.author=#{username}")
    int countByUser(String username);

    @Select("select date_format(c.create_time, '%Y-%m-%d') time, count(*) num from tb_comment c join tb_article a on c.article_id=a.id where a.author=#{username} group by date_format(create_time, '%Y-%m-%d')")
    List<SplineChart> chart(SysUser user);
}
