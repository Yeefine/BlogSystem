package com.myproject.blog.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.common.utils.SplineChart;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<SysUser> {

    @Select("select date_format(create_time, '%Y-%m-%d') time, count(*) num from tb_user group by date_format(create_time, '%Y-%m-%d')")
    List<SplineChart> chart();
}
