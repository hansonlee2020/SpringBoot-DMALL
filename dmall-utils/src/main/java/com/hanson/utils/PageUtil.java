package com.hanson.utils;

import java.io.Serializable;

/**
 * @CLassName PageUtil
 * @Description 分页处理工具
 * @Author li hao xin
 * @Date 2020/11/30 17:38
 **/
public class PageUtil implements Serializable {

    //开始索引
    private int start;
    //分页大小
    private int size;
    //当前页
    private int page;
    //分页大小
    private int limit;


    public PageUtil(){
    }

    public PageUtil(int current,int size){
        if (current > 0 && size > 0){
            this.start = (current - 1) * size;
            this.size = size;
            this.page = current;
            this.limit = size;
        }else new PageUtil();
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int current) {
        this.page = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    public void setSizeAndStart(){
        if (this.limit > 0 && this.page > 0){
            this.size = this.limit;
            this.start = (this.page - 1) * this.size;
        }
    }
}
