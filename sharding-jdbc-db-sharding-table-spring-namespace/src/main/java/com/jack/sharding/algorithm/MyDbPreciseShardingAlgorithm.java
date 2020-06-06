package com.jack.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String dbName : collection) {
            if (dbName.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                return dbName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
