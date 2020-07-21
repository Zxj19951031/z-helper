package org.zipper.helper.exception;

public interface IErrorCode {

    Integer getCode();

    String getMsg();

    @Override
    String toString();
}
