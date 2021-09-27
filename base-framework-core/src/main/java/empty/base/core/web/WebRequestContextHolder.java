package empty.base.core.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/27 - 23:41
 */
public class WebRequestContextHolder {

    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";
    public static final String CLIENT_IP = "clientIp";
    private static final ThreadLocal<Map<Object, Object>> resources = new WebRequestContextHolder.InheritableThreadLocalMap<>();

    public static String getToken() {
        return (String) get(TOKEN);
    }

    public static void setToken(String token) {
        put(TOKEN, token);
    }

    public static String getClientIp() {
        return (String) get(CLIENT_IP);
    }

    public static void setClientIp(String clientIp) {
        put(CLIENT_IP, clientIp);
    }

    public static String getUserId() {
        return (String) get(USER_ID);
    }

    public static void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public static Object get(Object key) {
        return getValue(key);
    }

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        } else if (value == null) {
            remove(key);
        } else {
            ensureResourcesInitialized();
            resources.get().put(key, value);
        }
    }

    public static void remove(Object key) {
        Map<Object, Object> perThreadResources = resources.get();
        if (perThreadResources != null) {
            perThreadResources.remove(key);
        }
    }

    public static void remove() {
        resources.remove();
    }

    private static void ensureResourcesInitialized() {
        if (resources.get() == null) {
            resources.set(new HashMap<>());
        }
    }

    private static Object getValue(Object key) {
        Map<Object, Object> perThreadResources = resources.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        private InheritableThreadLocalMap() {}

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            return parentValue != null ? (Map) ((HashMap) parentValue).clone() : null;
        }
    }
}
