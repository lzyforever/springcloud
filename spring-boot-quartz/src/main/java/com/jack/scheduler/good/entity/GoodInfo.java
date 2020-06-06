package com.jack.scheduler.good.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品基本信息
 */
@Entity
@Table(name = "basic_good_info")
@Data
public class GoodInfo {

    /**
     * 商品编号
     */
    @Id
    @GeneratedValue
    @Column(name = "bgi_id")
    private Long id;

    /**
     * 商品名称
     */
    @Column(name = "bgi_name")
    private String name;

    /**
     * 商品单价
     */
    @Column(name = "bgi_price")
    private BigDecimal price;

    /**
     * 商品单位
     */
    @Column(name = "bgi_unit")
    private String unit;

}
