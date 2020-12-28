package com.hanson.common.exception;


/**
 * @program: manager
 * @description: 分页信息错误，没有分页数据
 * @param:
 * @author: Hanson
 * @create: 2020-09-11 01:05
 **/
public class NullSplitPageInfoException extends RuntimeException {
    //自定义错误信息细节
    private String details;
    public NullSplitPageInfoException(){
        super();
    }
    public NullSplitPageInfoException(String details) {
        super();
        this.details = details;
    }

    public NullSplitPageInfoException(String message, String details) {
        super(message);
        this.details = details;
    }

    public NullSplitPageInfoException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }

    public NullSplitPageInfoException(Throwable cause, String details) {
        super(cause);
        this.details = details;
    }

    public NullSplitPageInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String details) {
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
