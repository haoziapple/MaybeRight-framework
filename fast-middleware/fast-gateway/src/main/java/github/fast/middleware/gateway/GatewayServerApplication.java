package github.fast.middleware.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication
@EnableZuulProxy
@EnableRedisHttpSession
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    /**
     * 同域名下部署时为了防止session冲突,可以设置session-cookie名称，默认为SESSION，
     *
     * @param name
     * @return
     */
    @Bean
    public DefaultCookieSerializer defaultCookieSerializer(@Value("${server.session.cookie.name:SESSION}") String name,
                                                           @Value("${server.session.cookie.max-age:-1}") Integer maxAge,
                                                           @Value("${server.session.timeout:1800}") Integer timeout,
                                                           RedisOperationsSessionRepository sessionRepository) {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName(name);
        cookieSerializer.setCookieMaxAge(maxAge);
        sessionRepository.setDefaultMaxInactiveInterval(timeout);
        return cookieSerializer;
    }
}
