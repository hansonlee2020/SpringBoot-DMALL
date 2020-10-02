package com.hanson.common.exception;

/**
 * @program: manager
 * @description: 系统异常
 * @param:
 * @author: Hanson
 * @create: 2020-09-18 18:25
 **/
public class DmallException extends RuntimeException {
    public DmallException() {
    }

    public DmallException(String message) {
        super(message);
    }

    public DmallException(String message, Throwable cause) {
        super(message, cause);
    }

    public DmallException(Throwable cause) {
        super(cause);
    }

    public DmallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
