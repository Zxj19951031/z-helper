package org.zipper.helper.exception;

/**
 * 框架错误码
 *
 * @author zhuxj
 */
public enum ErrorCode implements IErrorCode {

    SYSTEM_ERROR(1001, "系统内部异常"),
    PARAMETER_ERROR(1002, "参数异常"),
    IO_ERROR(1003, "IO异常"),
    UNKNOWN_TYPE(1004, "未知类型异常"),
    QUERY_DB_ERROR(1005, "数据库查询异常"),
    CLOSE_DB_ERROR(1006, "数据库连接关闭异常"),
    CLASS_NOT_FOUND(1007, "未发现类异常"),
    CONNECTION_FAILED(1008, "数据库连接异常"),
    SIGNATURE_ERROR(1009, "签名认证异常"),
    PERMISSION_ERROR(1010, "权限异常");

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("Code=[%s],Message=[%s]", this.code, this.msg);
    }
}
