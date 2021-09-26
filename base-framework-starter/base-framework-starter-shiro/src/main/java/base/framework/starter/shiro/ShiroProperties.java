package base.framework.starter.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/26 - 21:22
 */
@Component
@ConfigurationProperties(prefix = "base.shiro")
public class ShiroProperties {

    public final static String DEFAULT_LOGIN_URL = "/login";
    public final static String DEFAULT_SUCCESS_URL = "/success";
    public final static String DEFAULT_LOGOUT_URL = "/logout";
    public final static String DEFAULT_FORBIDDEN_URL = "/forbidden";

    /**
     * 登录url
     */
    private String loginUrl = DEFAULT_LOGIN_URL;
    /**
     * 登录成功url
     */
    private String successUrl = DEFAULT_SUCCESS_URL;
    /**
     * 退出url
     */
    private String logoutUrl = DEFAULT_LOGOUT_URL;
    /**
     * 禁止访问url
     */
    private String forbiddenUrl = DEFAULT_FORBIDDEN_URL;

    /**
     * platform:
     *   shiro:
     *     filterChainDefinition:
     *       /: anon
     *       /login: anon
     *       /test/**: anon
     *       /api/rolePri/**: anon
     *       /refresh/**: anon
     *       /env/**: anon
     *       /**: authc
     */
    private Map<String, String> filterChainDefinition = new HashMap<String, String>();

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getForbiddenUrl() {
        return forbiddenUrl;
    }

    public void setForbiddenUrl(String forbiddenUrl) {
        this.forbiddenUrl = forbiddenUrl;
    }

    public Map<String, String> getFilterChainDefinition() {
        return filterChainDefinition;
    }

    public void setFilterChainDefinition(Map<String, String> filterChainDefinition) {
        this.filterChainDefinition = filterChainDefinition;
    }
}
