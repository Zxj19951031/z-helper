package org.zipper.helper.mult.database;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
public class DynamicDatasourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDatasourceAspect.class);

    @Before("@annotation(db)")
    public void before(JoinPoint point, SwitchDB db) {
        String dbName = db.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dbName)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", db.name(), point.getSignature());
        } else {
            logger.debug("U se DataSource : {} > {}", dbName, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dbName);
        }
    }

    @After("@annotation(db)")
    public void restoreDataSource(JoinPoint point, SwitchDB db) {
        logger.debug("Revert DataSource : {} > {}", db.name(), point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
