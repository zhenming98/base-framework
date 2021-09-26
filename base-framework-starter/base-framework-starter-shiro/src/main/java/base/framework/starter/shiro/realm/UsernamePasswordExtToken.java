package base.framework.starter.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/26 - 21:39
 */
public class UsernamePasswordExtToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 6451972231621155494L;

    /**
     * 登录类型
     */
    private String loginType;
    /**
     * 扩展信息
     */
    private Map<String, String> ext = new HashMap<String, String>();

    public UsernamePasswordExtToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public UsernamePasswordExtToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }
}
