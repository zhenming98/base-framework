package empty.base.core.web;

import empty.base.core.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yzm
 * @date 2021/9/27 - 23:49
 */
public class HeaderFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("empty.base.core.web.HeaderFilter filter");
            }
            String token = Constants.getTokenFromRequest(request);
            String ip = HttpUtil.getIpAddr(request);
            WebRequestContextHolder.setClientIp(ip);
            if (token != null) {
                WebRequestContextHolder.setToken(token);
            }
            filterChain.doFilter(request, response);
        } finally {
            WebRequestContextHolder.remove();
        }
    }


}
