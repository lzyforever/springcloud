package com.jack.sharding.entity;

import com.jack.jdbc.annotation.Field;
import com.jack.jdbc.annotation.TableName;

import java.io.Serializable;

@TableName(value = "loudong", author = "jack", desc = "楼栋")
public class LouDong implements Serializable {

    private static final long serialVersionUID = -7910545051712412120L;

    @Field(value = "id", desc = "ID")
    private String id;

    @Field(value = "city", desc = "城市")
    private String city;

    @Field(value = "region", desc = "地区")
    private String region;

    @Field(value = "name", desc = "名称")
    private String name;

    @Field(value = "ld_num", desc = "楼栋号")
    private String ldNum;

    @Field(value = "unit_num", desc = "单元号")
    private String unitNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLdNum() {
        return ldNum;
    }

    public void setLdNum(String ldNum) {
        this.ldNum = ldNum;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }
}
