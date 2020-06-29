package org.zipper.helper.mult.database;

import java.lang.annotation.*;

/**
 * 自定义注解，切换数据源
 *
 * @author zhuxj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SwitchDB {
    String name();
}
