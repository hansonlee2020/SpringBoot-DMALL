package com.hanson.common.exception;

/**
 * @program: manager
 * @description: 分类信息异常，没有分类信息
 * @param:
 * @author: Hanson
 * @create: 2020-09-11 01:39
 **/
public class NullCategoryException extends RuntimeException{
    private String details;

    public NullCategoryException() {
        super();
    }

    public NullCategoryException(String details) {
        super();
        this.details = details;
    }

    public NullCategoryException(String message, String details) {
        super(message);
        this.details = details;
    }

    public NullCategoryException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }

    public NullCategoryException(Throwable cause, String details) {
        super(cause);
        this.details = details;
    }

    public NullCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String details) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
