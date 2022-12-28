package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.controller.exception.CustomException;
import com.yh.reggie.mapper.CategoryMapper;
import com.yh.reggie.mapper.DishMapper;
import com.yh.reggie.mapper.SetmealMapper;
import com.yh.reggie.pojo.Category;
import com.yh.reggie.pojo.Dish;
import com.yh.reggie.pojo.Setmeal;
import com.yh.reggie.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yu
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2022-12-27 11:21:42
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Override
    public boolean remove(Long id) {
        Category category = this.getById(id);
        int count;
        if(category.getType() == 1) {
            //菜品
            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            //查看菜品
            dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
            count = dishMapper.selectCount(dishLambdaQueryWrapper);
        }else{
            //套餐
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
            count = setmealMapper.selectCount(setmealLambdaQueryWrapper);
        }
        if (count > 0) {
            //已经关联 抛出业务异常
            throw new CustomException("当前分类已经关联其他菜品或套餐 无法删除");
        }
        //正常删除
        return this.removeById(id);
    }

    @Override
    public String getNameById(Long categoryId) {
        Optional<Long> optional = Optional.ofNullable(categoryId);
        List<Category> categoryList = categoryMapper.selectNameById(optional.orElse(0L));
        return categoryList.stream().map(Category::getName).collect(Collectors.toList()).get(0);
    }
}




