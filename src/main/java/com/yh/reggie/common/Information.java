package com.yh.reggie.common;

/**
 * date 2022/12/25
 *
 * @author yu
 */
public class Information {
    private Information(){

    }
    /**
     * 没有登陆时传到页面的数据
     */
    public static final String NOT_LOGIN = "NOTLOGIN";
    /**
     * 登陆时存储在session中的名称
     */
    public static final String USER_INFO = "userInfo";
    public static final String INITIAL_PASSWORD = "123456";
    /**
     * sql唯一约束
     * */
    public static final String INTEGRITY_CONSTRAINT = "Duplicate entry";
}
