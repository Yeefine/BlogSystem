package com.myproject.blog.common.auth;


import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义SessionManager，拦截前端请求，判断Token信息等
 */
public class AuthSessionManager extends DefaultWebSessionManager {

    public AuthSessionManager() {super();}

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 获取请求头Header中的Token，登录时定义了Token = SessionID
        return super.getSessionId(request, response);

    }
}
