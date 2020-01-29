package com.template.coe.demo.configs;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.getGroupConfig().setName("TestGroup");
        config.setInstanceName("hazelcast-instance-version1")
                .addMapConfig(
                        new MapConfig()
                        .setName("hazelConfigName")
                        .setMaxSizeConfig(
                                new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE)
                        )
                        .setEvictionPolicy(EvictionPolicy.LFU)
                        .setTimeToLiveSeconds(-1)
                );
        return config;
    }
}
