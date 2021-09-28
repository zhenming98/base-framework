package empty.base.starter.auth;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import empty.base.core.constants.Constants;
import empty.base.core.web.WebRequestContextHolder;
import empty.base.starter.auth.session.TokenSession;
import empty.base.starter.exception.exception.TokenAuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yzm
 * @date 2021/9/27 - 23:35
 */
public class TokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    @Resource
    TokenProperties tokenProperties;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("empty.base.starter.auth.TokenFilter filter");
        }
        String token = Constants.getTokenFromRequest(request);
        if (token != null && "".equals(token.trim())) {
            WebRequestContextHolder.setToken(token);
        }
        String requestUri = request.getRequestURI();
        if (urlInGlobalIgnore(requestUri) || urlInIgnore(requestUri)) {
            LOGGER.debug("url :{} in ignore urls", requestUri);
        } else {
            TokenSession tokenSession = TokenUtil.getTokenSession();
            if (tokenSession == null) {
                TokenAuthorizationException authorizationException = new TokenAuthorizationException();
                responseOutWithJson(response, authorizationException);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void responseOutWithJson(HttpServletResponse response, Object responseObject) {
        // 将实体对象转换为JSON Object转换
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            out.append(gson.toJson(responseObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean urlInIgnore(String requestUri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> ignoreUrls = tokenProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (antPathMatcher.match(ignoreUrl, requestUri)) {
                return true;
            }
        }
        return false;
    }

    private boolean urlInGlobalIgnore(String requestUri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> globalIgnoreUrls = Lists.newArrayList(tokenProperties.getGlobalIgnoreUrls());
        globalIgnoreUrls.add("/error");
        globalIgnoreUrls.add("/static/**");
        for (String ignoreUrl : globalIgnoreUrls) {
            if (antPathMatcher.match(ignoreUrl, requestUri)) {
                return true;
            }
        }
        return false;
    }
}
