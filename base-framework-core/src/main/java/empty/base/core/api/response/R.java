package empty.base.core.api.response;

/**
 * @author yzm
 * @date 2021/9/22 - 21:05
 */
public class R<T> extends BaseResponse<T>{

    private static final long serialVersionUID = -8116306212660906077L;

    public static R<?> success() {
        return new R<>();
    }

    public static <T> R<T> success(T data) {
        R<T> rsp = new R<T>();
        rsp.setData(data);
        return rsp;
    }

}
