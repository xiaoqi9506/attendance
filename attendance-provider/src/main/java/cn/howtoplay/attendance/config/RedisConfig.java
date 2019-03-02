package cn.howtoplay.attendance.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis缓存
 *
 * @author xiaoqi on 2019/1/28
 */

@Configuration
public class RedisConfig {

    @Value("${cache.host}")
    private String host;

    @Value("${cache.port}")
    private String port;

    @Value("${cache.password}")
    private String password;

    @Value("${cache.group}")
    private String group;

    @Bean
    public RedissonClient createCache() {
        Config config = new Config();
        //redis配置
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
//                                .setPassword(password)
                                .setClientName(group)
                                .setDatabase(0)
                                .setRetryAttempts(5);
        return Redisson.create(config);
    }
}
