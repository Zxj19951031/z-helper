package org.zipper.helper.quartz;

import org.zipper.helper.exception.IErrorCode;

public enum QuartzError implements IErrorCode {
    QUARTZ_ERROR(2001, "QUARTZ异常"),
    START_ERROR(2002, "启动实例异常"),
    ADD_LISTENER_ERROR(2003, "注册监听器异常"),
    INIT_ERROR(2004, "实例初始化异常"),
    PARAMETER_ERROR(2005, "参数异常"),
    CHECK_EXISTS_ERROR(2006, "校验任务存在异常"),
    ADD_JOB_ERROR(2007, "添加任务异常"),
    STOP_JOB_ERROR(2008, "停止任务异常"),
    INTERRUPT_JOB_ERROR(2009, "中断任务异常"),
    CRON_ERROR(2010, "调度表达式异常");

    private final Integer code;
    private final String msg;

    QuartzError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return String.format("Code=[%s],Message=[%s]", this.code, this.msg);
    }
}
