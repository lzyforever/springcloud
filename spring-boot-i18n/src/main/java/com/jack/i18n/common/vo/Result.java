package com.jack.i18n.common.vo;

import com.jack.i18n.common.i18n.ResourceUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.Clock;

/**
 * 统一实体返回对象
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2134018479136964408L;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回描述信息
     */
    private String msg;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 返回时间戳
     */
    private Long time;

    /**
     * 返回结果数据
     */
    private T data;

    public Result() {
        this.time = Clock.systemUTC().millis();
    }

    /**
     * 不传data 返回失败信息
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode("000000");
        result.setMsg(ResourceUtils.getEnvValueByKey("000000"));
        result.setSuccess(true);
        return result;
    }

    /**
     * 传data 返回成功信息
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("000000");
        result.setMsg(ResourceUtils.getEnvValueByKey("000000"));
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败信息
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode("000001");
        result.setMsg(ResourceUtils.getEnvValueByKey("000001"));
        result.setSuccess(false);
        return result;
    }

    /**
     * 返回失败信息
     */
    public static <T> Result<T> fail(String code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(ResourceUtils.getEnvValueByKey(code));
        result.setSuccess(false);
        return result;
    }

    /**
     * 异常返回(exception)
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode("-1");
        result.setMsg(message);
        result.setSuccess(false);
        return result;
    }

    /**
     * 异常返回(exception)
     */
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setCode("-1");
        result.setMsg(ResourceUtils.getEnvValueByKey("-1"));
        result.setSuccess(false);
        return result;
    }

    /**
     * 异常返回(exception)
     */
    public static <T> Result<T> error(String code, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(ResourceUtils.getEnvValueByKey(code));
        result.setData(data);
        result.setSuccess(false);
        return result;
    }
}
