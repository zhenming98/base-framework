package empty.base.starter.redisson;

import com.fasterxml.jackson.databind.introspect.ConcreteBeanPropertyBase;
import com.google.gson.Gson;
import empty.base.starter.redisson.properties.RedissonProperties;
import empty.base.starter.redisson.properties.SpringCacheProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.RedissonSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/25 - 18:08
 */
@Configuration
@EnableConfigurationProperties({SpringCacheProperties.class, RedissonProperties.class})
public class RedissonAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Resource
    SpringCacheProperties springCacheProperties;

    @Bean
    @Lazy
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RedissonClient redissonClient(RedissonProperties redissonProperties) throws IOException {
        if (redissonProperties.getSingleServerConfig().size() == 0) {
            LOGGER.warn("base.cache.redisson.singleServerConfig is null");
            return null;
        }
        return Redisson.create(Config.fromJSON(new Gson().toJson(redissonProperties)));
    }

    /**
     * spring CacheManager
     * @param redissonClient RedissonClient
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> cacheName = springCacheProperties.getCacheName();
        return new RedissonSpringCacheManager(redissonClient, cacheName);
    }

}

@EnableRedisHttpSession
@ConditionalOnProperty(havingValue = "true", name = "base.cache.enableRedisHttpSession")
class HttpSessionConfig{

    @Value("${server.session.timeout:1800}")
    private Integer sessionTimeout;

    /**
     * RedissonSessionRepository 配置，使sprign session timeout时间与sessionTimeout保持一致
     */
    @Bean
    @ConditionalOnProperty(name = "server.session.timeout")
    public RedissonSessionRepository sessionRepository(RedissonClient redissonClient, ApplicationEventPublisher eventPublisher) {
        RedissonSessionRepository repository = new RedissonSessionRepository(redissonClient, eventPublisher);
        repository.setDefaultMaxInactiveInterval(sessionTimeout);
        return repository;
    }

}
