package com.jack.mysql.entity;

import com.jack.jdbc.Orders;
import com.jack.jdbc.annotation.Field;
import com.jack.jdbc.annotation.TableName;

import java.io.Serializable;

@TableName(value="student", desc="学生表", author="jack")
public class Student implements Serializable {

    private static final long serialVersionUID = -6690784263770712827L;

    @Field(value="id", desc="主键ID")
    private String id;

    @Field(value="name", desc="学生名称")
    private String name;

    @Field(value="city", desc="所在城市")
    private String city;

    @Field(value="region", desc="所在区域")
    private String region;

    @Field(value="ld_num", desc="小区楼栋号")
    private String ldNum;

    @Field(value="unit_num", desc="楼栋单元号")
    private String unitNum;

    public Student() {
        super();
    }

    // 需要显示的字段名称，没有传入的字段则值为null
    public final static String[] SHOW_FIELDS = new String[]{ "city", "region", "name", "ld_num" };

    // 需要传入的字段名称，和传入的参数顺序相对应
    public final static String[] QUERY_FIELDS = new String[]{ "city", "region", "name" };

    public final static Orders[] ORDER_FIELDS = new Orders[] { new Orders("id", Orders.OrderType.ASC) };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
