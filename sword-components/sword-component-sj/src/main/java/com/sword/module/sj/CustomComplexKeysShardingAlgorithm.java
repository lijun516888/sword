package com.sword.module.sj;

import io.shardingsphere.core.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.Arrays;
import java.util.Collection;

public class CustomComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> collection1) {
        ShardingValue shardingValue = collection1.iterator().next();
        return Arrays.asList(shardingValue.getLogicTableName() + "_" + (((ListShardingValue) shardingValue).getValues().iterator().next()));
    }
}
