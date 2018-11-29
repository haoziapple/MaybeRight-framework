package com.haozi.mayberight.session.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author wanghao
 * @Description
 * @date 2018-11-28 9:59
 */
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
