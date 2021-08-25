package com.myproject.blog.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签类
 */
@Data
@TableName(value = "tb_tag")
public class SysTag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField(exist = false)  // 当前属性不在数据库表中
    private Long count;

    public SysTag() {
    }

    public SysTag(String name) {
        this.name = name;
    }
}
