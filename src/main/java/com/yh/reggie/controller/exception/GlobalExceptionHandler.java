package com.yh.reggie.controller.exception;

import com.yh.reggie.common.Information;
import com.yh.reggie.common.Result;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/25
 *
 * @author yu
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理sql异常 约束冲突约束
     * @return 传输给前端异常信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String > exceptionHandler(SQLIntegrityConstraintViolationException exception){
        //打印日志
        log.error(exception.getMessage());
        //判断是否有Duplicate entry 重复条目错误信息
        if (exception.getMessage().contains(Information.INTEGRITY_CONSTRAINT)) {
            //分割
            String[] split = exception.getMessage().split(" ");
            //将重复条目输出
            String msg = split[2] + "已存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }
    /**处理自己的异常*/
    @ExceptionHandler(CustomException.class)
    public Result<String> customHandler(CustomException exception){
        log.error(exception.getMessage());
        return Result.error(exception.getMessage());
    }
    /**处理io异常*/
    @ExceptionHandler(IOException.class)
    public Result<String> ioHandler(IOException exception){
        log.error(exception.getMessage());
        return Result.error("文件传输错误");
    }
    @ExceptionHandler(IllegalStateException.class)
    public Result<String> illegalStateHandler(IllegalStateException exception){
        log.error(exception.getMessage());
        return Result.error("非法状态");
    }
}
