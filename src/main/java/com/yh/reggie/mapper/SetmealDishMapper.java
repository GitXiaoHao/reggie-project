package com.yh.reggie.mapper;

import com.yh.reggie.pojo.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-12-28 12:50:15
* @Entity com.yh.reggie.pojo.SetmealDish
*/
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




