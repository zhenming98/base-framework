package base.framework.starter.shiro;

import base.framework.starter.shiro.realm.UsernamePasswordExtToken;
import empty.base.core.api.response.R;
import empty.base.starter.exception.exception.AuthorizationException;
import empty.base.starter.exception.exception.BaseException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yzm
 * @date 2021/9/26 - 21:41
 */
@Controller
public class DefaultShiroController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultShiroController.class);

    /**
     * 登录连接
     */
    @GetMapping(value = ShiroProperties.DEFAULT_LOGIN_URL)
    @ResponseBody
    public R<?> loginForm(Model model) {
        throw new AuthorizationException("用户未登录！");
    }

    /**
     * 登录
     */
    @PostMapping(value = ShiroProperties.DEFAULT_LOGIN_URL)
    @ResponseBody
    public R<?> login(@Validated @RequestBody LoginUser user) {
        String username = user.getUsername();
        UsernamePasswordExtToken token = new UsernamePasswordExtToken(username, user.getPassword(), user.isRememberMe());
        token.setExt(user.getExt());
        token.setLoginType(user.getLoginType());
        Subject currentUser = SecurityUtils.getSubject();
        try {
            LOGGER.debug("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            throw new UnauthorizedException("用户名密码不正确！");
        } catch (IncorrectCredentialsException ice) {
            LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            throw new UnauthorizedException("用户名密码不正确！");
        } catch (LockedAccountException lae) {
            LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            throw new UnauthorizedException("账号被锁定！");
        } catch (ExcessiveAttemptsException eae) {
            LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            throw new UnauthorizedException("登录太频繁！");
        } catch (AuthenticationException ae) {
            LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过");
            if (ae.getCause() instanceof BaseException) {
                throw new UnauthorizedException(ae.getCause().getMessage());
            }
            throw new UnauthorizedException("用户名密码不正确！");
        }
        if (currentUser.isAuthenticated()) {
            LOGGER.info("用户[" + username + "]登录认证通过");
            return R.success();
        }
        throw new UnauthorizedException("登录失败");
    }

    /**
     * 登录成功
     */
    @GetMapping(value = ShiroProperties.DEFAULT_SUCCESS_URL)
    @ResponseBody
    public R<?> success(Model model) {
        return R.success();
    }

    /**
     * 退出登录
     */
    @GetMapping(value = ShiroProperties.DEFAULT_LOGOUT_URL)
    @ResponseBody
    public R<?> logout() {
        SecurityUtils.getSubject().logout();
        return R.success();
    }

    /**
     * 没有权限
     */
    @RequestMapping(value = ShiroProperties.DEFAULT_FORBIDDEN_URL)
    @ResponseBody
    public String forbidden() {
        throw new AuthorizationException("没有访问权限！");
    }
}
