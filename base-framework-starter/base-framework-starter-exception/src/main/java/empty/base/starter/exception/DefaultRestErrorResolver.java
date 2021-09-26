package empty.base.starter.exception;

import empty.base.core.api.response.ExceptionResponse;
import empty.base.starter.exception.exception.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yzm
 * @date 2021/9/22 - 21:23
 */
@ControllerAdvice
public class DefaultRestErrorResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestErrorResolver.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse exceptionHandler(Exception e) {
        LOGGER.error("Exception!!!!!", e);
        if (e instanceof GeneralException) {
            return new ExceptionResponse(((GeneralException) e).getErrCode(), e.getMessage());
        }
        return new ExceptionResponse(e + " " + e.getMessage());
    }

}
