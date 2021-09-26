package empty.base.starter.exception.exception;

/**
 * @author yzm
 * @date 2021/9/22 - 21:18
 */
public class GeneralException extends BaseException{

    public static final String CODE = "E0000";

    public GeneralException(String message) {
        this(CODE, message);
    }

    public GeneralException(String errCode, String message) {
        super(errCode, message);
    }

    protected GeneralException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }
}
