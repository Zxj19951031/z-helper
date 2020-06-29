package org.zipper.helper.redis;

import org.zipper.helper.exception.IErrorCode;

public enum RedisError implements IErrorCode {
    INIT_ERROR(3001, "Redis实例初始化异常"),
    EXPIRE_TIME_ERROR(3002, "过期时间异常"),
    PARAMETER_ERROR(3003, "参数异常");

    RedisError(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

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
