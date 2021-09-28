package empty.base.starter.auth.session.token;

import empty.base.starter.auth.session.TokenSession;

import java.util.Map;
import java.util.UUID;

/**
 * @author yzm
 * @date 2021/9/27 - 23:10
 */
public abstract class TokenRepository {

    public String getNewToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建TokenSession
     *
     * @param userId userId
     * @param attrs  attrs
     * @return TokenSession
     */
    public abstract TokenSession create(String userId, Map<String, String> attrs);

    /**
     * 更新TokenSession
     *
     * @param tokenSession tokenSession
     */
    public abstract void update(TokenSession tokenSession);

    /**
     * 获取tokenSession 对象
     *
     * @param token String
     * @return tokenSession
     */
    public abstract TokenSession get(String token);

    /**
     * 删除token
     *
     * @param token String
     */
    public abstract void remove(String token);

}
