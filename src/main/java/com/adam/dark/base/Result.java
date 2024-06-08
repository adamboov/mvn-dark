package com.adam.dark.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author VAIO-adam
 */
@ApiModel(description = "返回对象 ")
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 7577366280198820635L;

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("返回数据对象")
    private T data;

    private long timestamp;

    private LocalDateTime dateTime;

    public Result() {
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.dateTime = LocalDateTime.now();
    }

    /**
     * 自定义返回
     *
     * @param code 自定义返回码
     * @param msg  返回msg
     * @param data 返回数据集
     * @return 返回对象
     */
    public static <T> Result<T> of(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

    /**
     * 自定义返回
     *
     * @param code 自定义返回码
     * @param msg  返回msg
     * @return 返回对象
     */
    public static <T> Result<T> of(int code, String msg) {
        return of(code, msg, null);
    }


    /**
     * 请求成功返回对象
     *
     * @return 返回对象
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 请求成功返回对象
     *
     * @param data 返回数据
     * @return 返回对象
     */
    public static <T> Result<T> ok(T data) {
        return of(200, "请求成功！", data);
    }


    /**
     * 错误时返回
     *
     * @param msg 错误信息
     * @return 返回对象
     */
    public static <T> Result<T> fail(String msg) {

        return of(400, msg, null);
    }


}
