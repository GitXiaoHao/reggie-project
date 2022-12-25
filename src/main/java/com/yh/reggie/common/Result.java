package com.yh.reggie.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * date 2022/12/25
 *
 * @author yu
 * 通用返回前端页面
 */
@Data
public class Result<T> {

    /**编码：1成功，0和其它数字为失败*/
    private Integer code;
    /**错误信息*/
    private String msg;
    /**数据*/
    private T data;
    /**动态数据*/
    private Map<Object,Object> map = new HashMap<>();

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
