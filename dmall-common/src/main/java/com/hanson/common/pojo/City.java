package com.hanson.common.pojo;

import java.io.Serializable;

/**
 * @description: 城市
 * @classname: City
 * @author: Hanson
 * @create: 2020/12/09
 **/
public class City implements Serializable {

    //国家
    String nation;

    //省区域
    String region;

    //市
    String city;

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "City{" +
                "nation='" + nation + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

}