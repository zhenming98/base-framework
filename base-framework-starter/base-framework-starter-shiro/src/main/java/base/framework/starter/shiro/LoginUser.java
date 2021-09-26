package base.framework.starter.shiro;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/26 - 21:30
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String password;
    private boolean rememberMe = false;
    private String loginType;
    private Map<String, String> ext = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
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
