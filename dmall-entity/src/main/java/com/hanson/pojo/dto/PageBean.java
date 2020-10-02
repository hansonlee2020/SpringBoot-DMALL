package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 存储分页的对象，用于系统返回分页信息，实现了序列化接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-11 20:01
 **/
@ApiModel(value = "分页对象",description = "封装分页信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> implements Serializable {
    //总数据量
    @ApiModelProperty(name = "total",value = "总数据量",dataType = "Long")
    private Long total;
    //当前的页数(currentPage)
    @ApiModelProperty(name = "currentPage",value = "当前的页数",dataType = "Integer")
    private Integer currentPage;
    //每页的数据量(pageSize)
    @ApiModelProperty(name = "pageSize",value = "每页的数据量",dataType = "Integer")
    private Integer pageSize;
    //页面的总数(total/pageSize)
    @ApiModelProperty(name = "totalPage",value = "页面的总数",dataType = "Integer")
    private Integer totalPage;
    //当前页面数据量 (直接=list.size())
    @ApiModelProperty(name = "size",value = "当前页面数据量",dataType = "Integer")
    private Integer size;
    //每个分页结果集
    @ApiModelProperty(name = "list",value = "每个分页结果集",dataType = "List")
    private List<T> list;

}
