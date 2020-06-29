package org.zipper.helper.web.response;

import com.alibaba.fastjson.JSON;
import org.zipper.helper.exception.HelperException;
import org.zipper.helper.exception.IErrorCode;

/**
 * @param <T> 包装的对象
 * @author zhuxj
 * @since 2020/06/29
 */
public class ResponseEntity<T> {

    //状态码
    private Integer code;
    //状态值
    private String message;
    //返回值
    private T data;

    /**
     * 成功返回的包装
     *
     * @param data 包装对象
     * @param <T>  包装对象类型
     * @return 包装体
     */
    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(data);
    }

    /**
     * 自定义异常的包装
     *
     * @param e 自定义异常
     * @return 包装体，包装了异常的message
     */
    public static ResponseEntity<String> error(HelperException e) {
        return error(e, e.getMessage());
    }

    /**
     * 带有数据的自定义异常包装
     *
     * @param e    自定义异常
     * @param data 包装数据
     * @param <T>  包装数据类
     * @return 包装体，包装数据
     */
    public static <T> ResponseEntity<T> error(HelperException e, T data) {
        return new ResponseEntity<>(e, data);
    }

    /**
     * 直接对错误码的包装
     *
     * @param error 错误码
     * @return 包装体
     */
    public static ResponseEntity<String> error(IErrorCode error) {
        return error(error, error.toString());
    }

    /**
     * 带有数据的错误码包装
     *
     * @param error 错误码
     * @param data  包装数据
     * @param <T>   包装类
     * @return 包装体
     */
    public static <T> ResponseEntity<T> error(IErrorCode error, T data) {
        return new ResponseEntity<>(error, data);
    }

    private ResponseEntity() {
    }

    private ResponseEntity(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    private ResponseEntity(IErrorCode error, T data) {
        this.code = error.getCode();
        this.message = error.getMsg();
        this.data = data;
    }

    private ResponseEntity(HelperException e, T data) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
