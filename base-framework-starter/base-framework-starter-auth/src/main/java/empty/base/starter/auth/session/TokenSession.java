package empty.base.starter.auth.session;

import java.io.Serializable;

/**
 * @Author yzm
 * @Date 2021/9/27 - 23:09
 */
public interface TokenSession extends Serializable {

    /**
     * 获取sessionId
     *
     * @return
     */
    String getToken();

    /**
     * 获取userid
     *
     * @return
     */
    String getUserId();

    /**
     * 设置用户ID
     */
    void setUserId(String userId);

    /**
     * 获取session 属性
     *
     * @param key 属性key
     * @return Attr
     */
    String getAttr(String key);

    /**
     * 设置session 属性
     *
     * @param val 属性值
     */
    void setAttr(String key, String val);

    /**
     * 刷新时间
     *
     * @return 刷新时间戳
     */
    long refreshAt();

    /**
     * 刷新过期时间
     */
    void refresh();

}

