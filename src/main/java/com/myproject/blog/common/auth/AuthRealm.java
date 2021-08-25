package com.myproject.blog.common.auth;

import com.myproject.blog.biz.entity.SysUser;
import com.myproject.blog.biz.service.UserService;
import com.myproject.blog.common.constants.enums.CommonEnum;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro Realm实现
 */
@Configuration
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 权限校验
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        if (username == null) { // Token已失效
            throw new AuthenticationException(CommonEnum.TOKEN_ERROR.getMsg());
        }
        String password = new String((char[]) authenticationToken.getCredentials());
        SysUser sysUser = userService.findByName(username);


        if (sysUser == null || !sysUser.getPassword().equals(password)) {   // 用户名或密码错误
            throw new IncorrectCredentialsException(CommonEnum.LOGIN_ERROR.getMsg());
        }

        return new SimpleAuthenticationInfo(
                sysUser,
                password,
                getName()   // 当前realm的名称
        );
    }
}
