package com.yh.reggie.mapper;

import com.yh.reggie.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-12-27 13:53:23
* @Entity com.yh.reggie.pojo.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




