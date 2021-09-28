package empty.base.starter.auth.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/27 - 23:12
 */
public class SampleTokenSession implements TokenSession {

    private static final long serialVersionUID = 8055687944498052758L;
    private String token;
    private String userId;
    private long createAt;
    private long refreshAt;
    private Map<String, String> attrs = new HashMap<>();

    public SampleTokenSession(String token, String userId) {
        this.token = token;
        this.userId = userId;
        long now = System.currentTimeMillis();
        this.createAt = now;
        this.refreshAt = now;
    }

    public SampleTokenSession(String token, String userId, Map<String, String> attrs) {
        this.token = token;
        this.userId = userId;
        this.attrs = attrs;
        long now = System.currentTimeMillis();
        this.createAt = now;
        this.refreshAt = now;
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    @Override
    public String getToken() {
        return token;
    }

    /**
     * 获取userid
     *
     * @return
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId
     */
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取session 属性
     *
     * @param key 属性key
     * @return Attr
     */
    @Override
    public String getAttr(String key) {
        return attrs.get(key);
    }

    /**
     * 设置session 属性
     *
     * @param key
     * @param val 属性值
     */
    @Override
    public void setAttr(String key, String val) {
        attrs.put(key, val);
    }

    /**
     * 刷新时间
     *
     * @return 刷新时间戳
     */
    @Override
    public long refreshAt() {
        return refreshAt;
    }

    /**
     * 刷新过期时间
     */
    @Override
    public void refresh() {
        this.refreshAt = System.currentTimeMillis();
    }
}
