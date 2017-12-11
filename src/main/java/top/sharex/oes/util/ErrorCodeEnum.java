package top.sharex.oes.util;

/**
 * @author danielyang
 * @Date 2017/11/10 17:13
 */
public enum ErrorCodeEnum {
    SUCCESS("成功", 0), PARAMETER_ERROR("参数错误", 101), UNKNOWN_ERROR("未知错误", 102);
    private String message;
    private Integer code;

    ErrorCodeEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
