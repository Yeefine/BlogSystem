package com.myproject.blog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 全局异常处理
 */
public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
