package org.zipper.helper.util.json;

import org.zipper.helper.exception.IErrorCode;

public enum JsonError implements IErrorCode {

    JSON_PARES_ERROR(3001, "JSON转换异常"),
    CONFIG_ERROR(3002, "JSON内容异常");

    private final Integer code;
    private final String msg;

    JsonError(int code, String msg) {
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
