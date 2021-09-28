package empty.base.starter.auth.session.token;

import com.google.gson.Gson;
import empty.base.starter.auth.TokenProperties;
import empty.base.starter.auth.session.SampleTokenSession;
import empty.base.starter.auth.session.TokenSession;
import empty.base.starter.exception.exception.TokenAuthorizationException;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/9/27 - 23:21
 */
public class RedisTokenRepository extends TokenRepository {

    private static final Gson GSON = new Gson();

    private RedissonClient redissonClient;
    private TokenProperties tokenProperties;

    public RedisTokenRepository(RedissonClient redissonClient, TokenProperties tokenProperties) {
        this.redissonClient = redissonClient;
        this.tokenProperties = tokenProperties;
    }

    /**
     * 创建TokenSession
     *
     * @param userId userId
     * @param attrs  attrs
     * @return TokenSession
     */
    @Override
    public TokenSession create(String userId, Map<String, String> attrs) {
        String token = getNewToken();
        TokenSession tokenSession;
        if (attrs == null) {
            tokenSession = new SampleTokenSession(token, userId);
        } else {
            tokenSession = new SampleTokenSession(token, userId, attrs);
        }
        put(tokenSession);
        return tokenSession;
    }

    /**
     * 更新TokenSession
     *
     * @param tokenSession tokenSession
     */
    @Override
    public void update(TokenSession tokenSession) {
        String token = tokenSession.getToken();
        TokenSession session = get(token);
        if (session == null) {
            throw new TokenAuthorizationException();
        }
        put(tokenSession);
    }

    /**
     * 获取tokenSession 对象
     *
     * @param token String
     * @return tokenSession
     */
    @Override
    public TokenSession get(String token) {
        RMapCache<String, String> mapCache = getMapCache();
        String tokenSessionJson = mapCache.get(token);
        SampleTokenSession sampleTokenSession = GSON.fromJson(tokenSessionJson, SampleTokenSession.class);
        refreshTtl(sampleTokenSession);
        return sampleTokenSession;
    }

    /**
     * 删除token
     *
     * @param token String
     */
    @Override
    public void remove(String token) {
        RMapCache<String, String> mapCache = getMapCache();
        mapCache.remove(token);
    }

    private RMapCache<String, String> getMapCache() {
        String cacheName = "UAC.LOGIN_TOKEN";
        return redissonClient.getMapCache(cacheName.toUpperCase());
    }

    private void put(TokenSession tokenSession) {
        RMapCache<String, String> mapCache = getMapCache();
        if (tokenSession != null) {
            tokenSession.refresh();
            String token = tokenSession.getToken();
            String json = GSON.toJson(tokenSession);
            mapCache.put(token, json, tokenProperties.getExpiredInSeconds(), TimeUnit.SECONDS);
        }
    }

    private void refreshTtl(TokenSession tokenSession) {
        if (tokenSession == null) {
            return;
        }
        long now = System.currentTimeMillis();
        if (tokenProperties.getExpiredInSeconds() >= 3600 * 24) {
            long refreshTime = tokenSession.refreshAt();
            if (Math.abs(now - refreshTime) < 3600 * 2L) {
                put(tokenSession);
            }
        } else {
            put(tokenSession);
        }
    }
}
