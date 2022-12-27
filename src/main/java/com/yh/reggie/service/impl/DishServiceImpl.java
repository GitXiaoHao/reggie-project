package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.controller.exception.CustomException;
import com.yh.reggie.mapper.DishMapper;
import com.yh.reggie.pojo.Dish;
import com.yh.reggie.pojo.DishFlavor;
import com.yh.reggie.pojo.dto.DishDto;
import com.yh.reggie.service.DishFlavorService;
import com.yh.reggie.service.DishService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yu
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-12-27 13:53:23
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public boolean saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息
        boolean save = this.save(dishDto);
        if (save) {
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

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = this.getById(Optional.ofNullable(id).orElse(0L));
        //查询当前菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, Optional.ofNullable(dish.getId()).orElse(0L))
                .orderByAsc(DishFlavor::getUpdateTime);
        //对应的口味
        List<DishFlavor> dishFlavorList = dishFlavorService.list(wrapper);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(dishFlavorList);
        return dishDto;
    }

    @Override
    public boolean updateWithFlavor(DishDto dishDto) {
        //更新菜品信息
        boolean update = this.updateById(dishDto);
        //清理当前菜品对应口味数据
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        update = dishFlavorService.remove(wrapper);
        //添加当前提交的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        //使用流设置自己id
        flavors = flavors.stream()
                          .peek(flavor -> flavor.setId(null))
                          .collect(Collectors.toList());
        //保存口味
        update = dishFlavorService.saveBatch(flavors);
        return update;
    }
}




