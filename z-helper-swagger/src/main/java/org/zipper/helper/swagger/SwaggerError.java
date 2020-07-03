package org.zipper.helper.swagger;

import org.zipper.helper.exception.IErrorCode;

public enum SwaggerError implements IErrorCode {
    API_JSON_ERROR(4001, "API Json配置异常");

    SwaggerError(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("Code=[%s],Message=[%s]", this.code, this.msg);
    }
}