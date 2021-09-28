package empty.base.starter.auth;

import empty.base.core.web.WebRequestContextHolder;
import empty.base.starter.auth.session.TokenSession;
import empty.base.starter.auth.session.token.TokenRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yzm
 * @date 2021/9/27 - 23:35
 */
public class TokenUtil {

    public static TokenRepository tokenRepository;

    static void setTokenRepository(TokenRepository tokenRepository) {
        if (TokenUtil.tokenRepository == null) {
            TokenUtil.tokenRepository = tokenRepository;
        }
    }

    /**
     * 获取当前token session
     */
    public static TokenSession getTokenSession() {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return tokenRepository.get(token);
    }

    /**
     * 获取当前token关联的用户ID
     *
     * @return
     */
    public static String getUserId() {
        TokenSession tokenSession = getTokenSession();
        if (tokenSession == null) {
            return null;
        }
        return tokenSession.getUserId();
    }

    /**
     * 获取当前token关联的用户ID
     *
     * @return
     */
    public static Long getUserIdAsLong() {
        String userId = getUserId();
        if (userId == null) {
            return null;
        }
        return Long.valueOf(userId);
    }

    /**
     * 重新设置token关联的用户ID
     *
     * @param userId
     */
    public static void resetUserId(String userId) {
        TokenSession tokenSession = getTokenSession();
        assert tokenSession != null;
        tokenSession.setUserId(userId);
        tokenRepository.update(tokenSession);
    }

    /**
     * 重新设置token关联的用户ID
     *
     * @param userId
     */
    public static void resetUserIdAsLong(Long userId) {
        resetUserId(String.valueOf(userId));
    }

    /**
     * 清除token
     */
    public static void removeToken() {
        String token = getToken();
        if (StringUtils.isNotEmpty(token)) {
            tokenRepository.remove(token);
        }
    }

    /**
     * 获取token
     *
     * @return token
     */
    private static String getToken() {
        return WebRequestContextHolder.getToken();
    }


}
