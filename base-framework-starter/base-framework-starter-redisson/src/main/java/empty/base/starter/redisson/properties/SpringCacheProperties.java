package empty.base.starter.redisson.properties;

import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/25 - 18:08
 */
@Component
@ConfigurationProperties(prefix = "base.cache")
public class SpringCacheProperties {

    private Map<String, CacheConfig> cacheName = new HashMap<>();

    public Map<String, CacheConfig> getCacheName() {
        return cacheName;
    }

    public void setCacheName(Map<String, CacheConfig> cacheName) {
        this.cacheName = cacheName;
    }
}
