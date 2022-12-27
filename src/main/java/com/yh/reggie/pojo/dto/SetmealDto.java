package com.yh.reggie.pojo.dto;


import com.yh.reggie.pojo.Setmeal;
import com.yh.reggie.pojo.SetmealDish;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetmealDto extends Setmeal {
    /**
     * 套餐关联的菜品集合
     */
    @NotNull(message = "菜品不能为空")
    private List<SetmealDish> setmealDishes;
    /**
     * 分类名称
     */
    private String categoryName;
}