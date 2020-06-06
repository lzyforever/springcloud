package com.jack.scheduler.good.repository;

import com.jack.scheduler.good.entity.GoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商品数据操作JPA接口
 */
public interface GoodRepository extends JpaRepository<GoodInfo, Long> {
}
