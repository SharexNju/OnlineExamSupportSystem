package top.sharex.oes.vo;

import top.sharex.oes.util.ErrorCodeEnum;

/**
 * @author danielyang
 * @Date 2017/11/10 17:13
 */
public class ResponseVO<T> {
    public ErrorCodeEnum errorCodeEnum;
    public T data;
    public boolean isSuccess;

    public static <T> ResponseVO<T> newInstance(T data) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.data = data;
        responseVO.errorCodeEnum = ErrorCodeEnum.SUCCESS;
        responseVO.isSuccess = true;
        return responseVO;
    }

    public static <T> ResponseVO<T> newInstance(ErrorCodeEnum errorCodeEnum) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.errorCodeEnum = errorCodeEnum;
        responseVO.isSuccess = true;
        return responseVO;
    }

}
