package org.zipper.demo.zdemo;

import io.swagger.annotations.Api;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zipper.helper.shiro.BaseLoginController;
import org.zipper.helper.shiro.BaseUser;
import org.zipper.helper.web.response.ResponseEntity;

@Api
@RestController
@RequestMapping("demo")
public class DemoController extends BaseLoginController<ResponseEntity<String>> {
    @Override
    public ResponseEntity<String> login(BaseUser user) {
        try {
            super.login(user);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        return ResponseEntity.success("success");
    }
}
