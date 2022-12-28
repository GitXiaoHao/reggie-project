package com.yh.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yh.reggie.pojo.Employee;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    private final String createUser = "createUser";
    private final String updateUser = "updateUser";
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("[insert]");
        log.info(metaObject.toString());
        Long id = (Long) BaseContext.getCurrent();
        log.info(id.toString());
        String createTime = "createTime";
        if(metaObject.hasGetter(createTime)) {
            metaObject.setValue(createTime, LocalDateTime.now());
        }
        String updateTime = "updateTime";
        if (metaObject.hasGetter(updateTime)) {
            metaObject.setValue(updateTime, LocalDateTime.now());
        }
        if (metaObject.hasGetter(createUser)) {
            metaObject.setValue(createUser, id);
        }
        if (metaObject.hasGetter(updateUser)) {
            metaObject.setValue(updateUser, id);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("[update]");
        log.info(metaObject.toString());
        Long id = (Long) BaseContext.getCurrent();
        log.info(id.toString());
        if (metaObject.hasGetter(createUser)) {
            metaObject.setValue(createUser, id);
        }
        if (metaObject.hasGetter(updateUser)) {
            metaObject.setValue(updateUser, id);
        }
    }
}
