package org.zipper.helper.exception;

/**
 * 包装异常
 *
 * @author zhuxj
 */
public class HelperException extends RuntimeException {
    private IErrorCode error;

    private HelperException() {

    }

    private HelperException(IErrorCode error) {
        super(error.toString());
        this.error = error;
    }

    private HelperException(IErrorCode error, String msg) {
        super(msg);
        this.error = error;
    }

    private HelperException(IErrorCode error, Throwable e) {
        super(e.getMessage());
        this.error = error;
    }

    public static HelperException newException(IErrorCode error) {
        return new HelperException(error);
    }

    public static HelperException newException(IErrorCode error, String msg) {
        return new HelperException(error, msg);
    }

    public static HelperException newException(IErrorCode error, Throwable e) {
        return new HelperException(error, e);
    }

    public static HelperException newException(Throwable e) {
        return new HelperException(ErrorCode.SYSTEM_ERROR, e);
    }

    public String getMsg() {
        return this.error.getMsg();
    }

    public int getCode() {
        return this.error.getCode();
    }

    public String getTotalMessage() {
        return String.format("%s,Detail=[%s]", error.toString(), getMessage());
    }
}
