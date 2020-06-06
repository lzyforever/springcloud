package com.jack.sharding.algorithm;

import com.jack.sharding.config.ShardingRangeConfig;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.*;

public class MyDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    private static List<ShardingRangeConfig> configs = new ArrayList<>();

    static {
        ShardingRangeConfig config = new ShardingRangeConfig();
        config.setStart(0);
        config.setEnd(49);
        config.setDatasourceList(Arrays.asList("ds0", "ds1"));
        configs.add(config);

        config = new ShardingRangeConfig();
        config.setStart(50);
        config.setEnd(100);
        config.setDatasourceList(Arrays.asList("dss0", "dss1"));
        configs.add(config);
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Optional<ShardingRangeConfig> configOptional = configs.stream().filter(
                c -> shardingValue.getValue() >= c.getStart() && shardingValue.getValue() <= c.getEnd()).findFirst();
        if (configOptional.isPresent()) {
            ShardingRangeConfig rangeConfig = configOptional.get();
            for (String ds : rangeConfig.getDatasourceList()) {
                if (ds.endsWith(shardingValue.getValue() % 2 + "")) {
                    System.err.println(ds);
                    return ds;
                }
            }
        }
        throw new IllegalArgumentException();
    }
}