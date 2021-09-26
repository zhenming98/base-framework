package empty.base.core.api.response;

import org.springframework.http.HttpStatus;

/**
 * @author yzm
 * @date 2021/9/22 - 21:10
 */
public class ExceptionResponse {

    public static final String DEFAULT_CODE = "E0000";

    private final HttpStatus status;
    private final String errCode;
    private final String errMsg;

    public ExceptionResponse(String errMsg) {
        this.status = HttpStatus.OK;
        this.errCode = DEFAULT_CODE;
        this.errMsg = errMsg;
    }

    public ExceptionResponse(String errCode, String errMsg) {
        this.status = HttpStatus.OK;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ExceptionResponse(HttpStatus status, String errCode, String errMsg) {
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
