package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Setmeal;
import com.yh.reggie.pojo.dto.SetmealDto;
import com.yh.reggie.service.SetmealService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/28
 *
 * @author yu
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     */
    @CacheEvict(value = "setmeal", allEntries = true)
    @PostMapping
    public Result<String> save(@Valid @RequestBody SetmealDto setmealDto, BindingResult result) {
        if (result.hasErrors()) {
            //如果有值
            return Result.error(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        boolean save = setmealService.saveWithDish(setmealDto);
        return save ? Result.success("新增成功") : Result.error("新增失败");
    }
    @Cacheable(value = "setmeal", key = "#page + '_' + #pageSize")
    @GetMapping("/page")
    public Result<Page<SetmealDto>> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        //根据name模糊查询
        wrapper.like(name != null, Setmeal::getName, name)
                .orderByAsc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, wrapper);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        setmealDtoPage.setRecords(pageInfo.getRecords()
                                          .stream()
                                          .map(setmeal -> {
                                              return setmealService.getByIdWithDishAndCategoryName(setmeal.getId());
                                          })
                                          .collect(Collectors.toList()));
        return Result.success(setmealDtoPage);
    }

    /**
     * 批量删除
     *
     * @param ids id数组
     * @return
     */
    @CacheEvict(value = "setmeal", allEntries = true)
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        boolean remove = setmealService.removeWithDish(ids, true);
        return remove ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 批量的停售和起售
     *
     * @return
     */
    @CacheEvict(value = "setmeal",allEntries = true)
    @PostMapping("/status/{status}")
    public Result<String> status(@PathVariable("status") Integer status, @RequestParam Long[] ids) {
        //创建dish集合
        List<Setmeal> setmealList = new ArrayList<>();
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealList.add(setmeal);
        }
        boolean update = setmealService.updateBatchById(setmealList);
        return update ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 根据id获取信息
     *
     * @param id
     * @return
     */
    @Cacheable(value = "setmeal", key = "#id")
    @GetMapping("/{id}")
    public Result<SetmealDto> getById(@PathVariable("id") Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithDishAndCategoryName(Optional.ofNullable(id).orElse(0L));
        log.info(setmealDto.toString());
        return Result.success(setmealDto);
    }

    /**
     * 修改套餐
     *
     * @return
     */
    @CacheEvict(value = "setmeal", allEntries = true)
    @PutMapping
    public Result<String> update(@Valid @RequestBody SetmealDto setmealDto, BindingResult result) {
        if (result.hasErrors()) {
            //如果有值
            return Result.error(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        boolean save = setmealService.updateWithDish(setmealDto);
        return save ? Result.success("修改成功") : Result.error("修改失败");
    }

    @Cacheable(value = "setmeal" , key = "#setmeal.categoryId + '_' + #setmeal.status")
    @GetMapping("/list")
    public Result<List<SetmealDto>> listResult(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getName() != null, Setmeal::getName, setmeal.getName())
                .eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, 1)
                .orderByAsc(Setmeal::getUpdateTime);
        return Result.success(setmealService.list(wrapper)
                                      .stream()
                                      .map(item -> setmealService.getByIdWithDishAndCategoryName(item.getId()))
                                      .collect(Collectors.toList()));
    }
}
