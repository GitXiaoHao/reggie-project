package com.yh.reggie.pojo.dto;


import com.yh.reggie.pojo.Dish;
import com.yh.reggie.pojo.DishFlavor;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DishDto extends Dish {
    @NotEmpty(message = "口味不能为空")
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}