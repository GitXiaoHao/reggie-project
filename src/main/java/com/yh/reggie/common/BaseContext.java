package com.yh.reggie.common;

/**
 * date 2022/12/26
 *
 * @author yu
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户
 */
public class BaseContext {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    private BaseContext() {
    }

    public static Object getCurrent() {
        return THREAD_LOCAL.get();
    }

    public static void setCurrent(Object o) {
        THREAD_LOCAL.set(o);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
