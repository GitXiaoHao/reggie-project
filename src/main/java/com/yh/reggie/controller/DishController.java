package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Dish;
import com.yh.reggie.pojo.dto.DishDto;
import com.yh.reggie.service.CategoryService;
import com.yh.reggie.service.DishService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增
     */
    @PostMapping
    public Result<String> save(@Validated @RequestBody DishDto dishDto, BindingResult result) {
        //判断口味是否有值
        if (result.hasErrors()) {
            //如果有值
            return Result.error(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        boolean save = dishService.saveWithFlavor(dishDto);
        return save ? Result.success("新增成功") : Result.error("新增失败");
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<DishDto>> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        //添加模糊查询
        wrapper.like(name != null, Dish::getName, name)
                //添加排序
                .orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(dishPage, wrapper);
        //对象拷贝 忽略查询出来的集合
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        //使用dto来获取菜品分类
        List<DishDto> dishDtoList
                = dishPage.getRecords()
                          .stream()
                          .map(dish -> {
                              DishDto dto = new DishDto();
                              //对象拷贝
                              BeanUtils.copyProperties(dish, dto);
                              //根据分类id查询分类
                              List<String> nameList = categoryService.getNameById(dish.getCategoryId());
                              if(nameList != null) {
                                  //赋值给dto对象
                                  dto.setCategoryName(nameList.get(0));
                              }
                              return dto;
                          })
                          .collect(Collectors.toList());
        //获得菜品集合
        dishDtoPage.setRecords(dishDtoList);
        return Result.success(dishDtoPage);
    }

    /**
     * 根据id查询数据 菜品信息和口味信息
     * @param id 传输的id
     * @return dto
     */
    @GetMapping("/{id}")
    public Result<DishDto> getById(@PathVariable("id") Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(Optional.ofNullable(id).orElse(0L));
        return Result.success(dishDto);
    }

    @PutMapping
    public Result<String> update(@Validated @RequestBody DishDto dishDto, BindingResult result) {
        //判断口味是否有值
        if (result.hasErrors()) {
            //如果有值
            return Result.error(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        boolean save = dishService.updateWithFlavor(dishDto);
        return save ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 批量的停售和起售
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> status(@PathVariable("status") Integer status,@RequestParam Long[] ids){
            //创建dish集合
        List<Dish> dishList = new ArrayList<>();
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishList.add(dish);
        }
        boolean update = dishService.updateBatchById(dishList);
        return update ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 批量的删除
     *
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        //创建集合
        boolean remove = dishService.removeByIds(ids);
        return remove ? Result.success("删除成功") : Result.error("删除失败");
    }
    /**
     * 根据条件查询数据
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        //如果传过来的有id
        wrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId,dish.getCategoryId())
                //如果进行查询
                .eq(dish.getName() != null,Dish::getName,dish.getName())
                //只要起售菜品
                .eq(Dish::getStatus,1)
                //排序
                .orderByAsc(Dish::getSort)
                .orderByAsc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(wrapper);
        return Result.success(dishList);
    }
}
