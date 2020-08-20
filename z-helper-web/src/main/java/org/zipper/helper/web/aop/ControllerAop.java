package org.zipper.helper.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;
import org.zipper.helper.web.response.ResponseEntity;

/**
 * 全局ControllerAOP，
 * 通过使用@ImportResource("classpath:spring-aop.xml")进行配置
 *
 * @author zhuxj
 */
public class ControllerAop {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResponseEntity<?> result;

        try {
            result = (ResponseEntity<?>) pjp.proceed();
            logger.info(pjp.getSignature() + " 耗时 : " + (System.currentTimeMillis() - startTime) + " ms");
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }

        return result;
    }

    /**
     * 封装异常信息，注意区分已知异常（自己抛出的）和未知异常
     */
    private ResponseEntity<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResponseEntity<?> result;

        // 已知异常
        if (e instanceof HelperException) {
            result = ResponseEntity.error((HelperException) e);
        } else {
            logger.error(pjp.getSignature() + " 方法异常 ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result = ResponseEntity.error(HelperException.newException(ErrorCode.SYSTEM_ERROR, "非预期异常，请联系管理员"));
        }

        return result;
    }
}
