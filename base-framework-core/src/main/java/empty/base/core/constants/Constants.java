package empty.base.core.constants;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yzm
 * @date 2021/9/27 - 23:50
 */
public class Constants {

    private Constants() {}

    public static abstract class HttpHeader {
        private HttpHeader() {}

        public static final String HEADER_REQUEST_TIMESTAMP = "x-timestamp";
        public static final String HEADER_REQUEST_TOKEN = "x-token";
        public static final String HEADER_APP_ID = "x-app-id";
        public static final String HEADER_APP_TOKEN = "x-app-token";
    }

    /**
     * 从http request 中获取token
     *
     * @param request http request
     * @return token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        return trimHeaderValue(request, HttpHeader.HEADER_REQUEST_TOKEN);
    }

    /**
     * 从http request 中获取timestamp
     *
     * @param request http request
     * @return timestamp
     */
    public static String getTimestampFromRequest(HttpServletRequest request) {
        return trimHeaderValue(request, Constants.HttpHeader.HEADER_REQUEST_TIMESTAMP);
    }

    /**
     * 从http request 中获取 app-id
     *
     * @param request http request
     * @return Authorization
     */
    public static String getAppIdFromRequest(HttpServletRequest request) {
        return trimHeaderValue(request, HttpHeader.HEADER_APP_ID);
    }

    /**
     * 从http request 中获取app token
     *
     * @param request http request
     * @return v
     */
    public static String getAppToken(HttpServletRequest request) {
        return trimHeaderValue(request, HttpHeader.HEADER_APP_TOKEN);
    }

    private static String trimHeaderValue(HttpServletRequest request, String header){
        return StringUtils.trim(request.getHeader(header));
    }

}
