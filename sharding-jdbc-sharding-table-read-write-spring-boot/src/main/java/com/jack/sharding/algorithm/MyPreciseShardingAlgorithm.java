package com.jack.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 自定义分片算法
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String tableName: collection) {
            if (tableName.endsWith(preciseShardingValue.getValue() % 4 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
