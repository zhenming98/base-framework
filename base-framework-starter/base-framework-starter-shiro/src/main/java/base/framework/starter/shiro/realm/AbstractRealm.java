package base.framework.starter.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yzm
 * @date 2021/9/26 - 21:32
 */
public abstract class AbstractRealm extends AuthorizingRealm implements InitializingBean {

    /**
     * 认证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        throw new UnknownAccountException();
    }

    /**
     * 加载用户角色及权限，用来验证用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        throw new AuthorizationException();
    }

    @Override
    public void afterPropertiesSet() {
        setCredentialsMatcher(hashedCredentialsMatcher());
    }

    /**
     * 凭证匹配器  默认SHA-256算法， 1024次HASH
     */
    protected CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1024);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        // hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

}
