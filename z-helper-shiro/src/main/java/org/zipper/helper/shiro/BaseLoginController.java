package org.zipper.helper.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;

public abstract class BaseLoginController<T> {

    public T login(BaseUser user) throws AuthenticationException, AuthorizationException {
        SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
        return null;
    }
}
