package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.Dish;
import com.yh.reggie.pojo.DishFlavor;
import com.yh.reggie.pojo.dto.DishDto;
import com.yh.reggie.service.DishFlavorService;
import com.yh.reggie.service.DishService;
import com.yh.reggie.mapper.DishMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author yu
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-12-27 13:53:23
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    public boolean saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息
        boolean save = this.save(dishDto);
        if(save){
            //菜品id
            Long dishDtoId = dishDto.getId();
            //获得口味 已在controller判断是否为空
            List<DishFlavor> flavors = dishDto.getFlavors();
            //使用流设置菜品id
            flavors = flavors.stream()
                    .peek(flavor -> flavor.setDishId(dishDtoId))
                              .collect(Collectors.toList());
            //保存菜品口味表
            save = dishFlavorService.saveBatch(flavors);
        }
        return save;
    }
}




