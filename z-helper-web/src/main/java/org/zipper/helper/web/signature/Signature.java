package org.zipper.helper.web.signature;

import java.lang.annotation.*;

/**
 * 签名注解，标注在方法/类上作为切点
 *
 * @author zhuxj
 * @since 2020/08/24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Signature {
}