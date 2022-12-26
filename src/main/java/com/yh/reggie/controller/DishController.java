package com.yh.reggie.controller;

import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.dto.DishDto;
import com.yh.reggie.service.DishFlavorService;
import com.yh.reggie.service.DishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/27
 *
 * @author yu
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    /**新增*/
    @PostMapping
    public Result<String> save(@Validated @RequestBody DishDto dishDto, BindingResult result){
        //判断口味是否有值
        if (result.hasErrors()) {
            //如果有值
            return Result.error(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        boolean save = dishService.saveWithFlavor(dishDto);
        return save ? Result.success("新增成功") : Result.error("新增失败");
    }
}
