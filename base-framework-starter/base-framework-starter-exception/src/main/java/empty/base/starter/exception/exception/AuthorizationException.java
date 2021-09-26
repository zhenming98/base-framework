package empty.base.starter.exception.exception;

/**
 * @author yzm
 * @date 2021/9/26 - 21:44
 */
public class AuthorizationException extends BaseException{

    public static final String CODE = "E1000";

    public AuthorizationException(String message) {
        this(CODE, message);
    }

    public AuthorizationException(String errCode, String message) {
        super(errCode, message);
    }

    protected AuthorizationException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }
}
