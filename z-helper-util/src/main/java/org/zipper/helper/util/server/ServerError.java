package org.zipper.helper.util.server;

import org.zipper.helper.exception.IErrorCode;

public enum ServerError implements IErrorCode {

    MKDIR_ERROR(5001, "执行mkdir异常"),
    CONNECT_ERROR(5002, "连接服务器异常"),
    PUT_ERROR(5003, "执行put异常"),
    BUILD_ERROR(5004, "构建实例失败");

    private final Integer code;
    private final String msg;

    ServerError(int code, String msg) {
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