package com.yh.reggie.service;

import com.yh.reggie.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.reggie.pojo.dto.DishDto;

import org.springframework.transaction.annotation.Transactional;

/**
* @author yu
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-12-27 13:53:23
*/
public interface DishService extends IService<Dish> {
    /**
     * 新增菜品 同时插入菜品对应的口味类型 需要操作两张表
     * @param dishDto dto 数据传输对象 里面分装了口味集合
     * @return 操作成功返回true
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息和口味信息
     * @param id id
     * @return dto
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 修改菜品信息 更新对应的口味信息
     * @param dishDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateWithFlavor(DishDto dishDto);
}
