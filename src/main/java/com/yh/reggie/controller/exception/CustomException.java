package com.yh.reggie.controller.exception;

/**
 * date 2022/12/27
 *
 * @author yu
 * 自定义业务异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String massage) {
        super(massage);
    }
}
