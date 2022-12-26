package com.yh.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yh.reggie.pojo.Employee;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/26
 *
 * @author yu
 * 元数据对象处理器
 */
@Component
@Slf4j
public class MetaObjectHandlerUtil implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("[insert]");
        log.info(metaObject.toString());
        Employee employee = (Employee) BaseContext.getCurrent();
        log.info(employee.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", employee.getId());
        metaObject.setValue("updateUser", employee.getId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("[update]");
        log.info(metaObject.toString());
        Employee employee = (Employee) BaseContext.getCurrent();
        log.info(employee.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", employee.getId());
    }
}
