package empty.base.starter.exception.exception;

/**
 * @author yzm
 * @date 2021/9/27 - 23:04
 */
public class TokenAuthorizationException extends BaseException{

    public static final String CODE = "E2000";
    public static final String MESSAGE = "need to login";

    public TokenAuthorizationException() {
        this(CODE, MESSAGE);
    }

    public TokenAuthorizationException(String message) {
        this(CODE, message);
    }

    public TokenAuthorizationException(String errCode, String message) {
        super(errCode, message);
    }

    protected TokenAuthorizationException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }
}
