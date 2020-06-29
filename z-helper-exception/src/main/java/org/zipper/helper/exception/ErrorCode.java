package org.zipper.helper.exception;

/**
 * 框架错误码
 *
 * @author zhuxj
 */
public enum ErrorCode implements IErrorCode {

    SYSTEM_ERROR(1001, "系统内部错误"),
    PARAMETER_ERROR(1002, "参数错误");

    ErrorCode(Integer code, String msg) {
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
