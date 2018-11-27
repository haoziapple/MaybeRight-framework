package com.haozi.mayberight.session.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanghao
 * @Description
 * @date 2018-11-27 14:59
 */
@ConditionalOnProperty(name = "default.session.store-type", havingValue = "map")
@Configuration
@EnableSpringHttpSession
public class HttpSessionConfig {
    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @Bean
    public MapSessionRepository sessionRepository() {
          return new MapSessionRepository(sessions);
      }
}
