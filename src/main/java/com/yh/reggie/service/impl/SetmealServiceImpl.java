package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.controller.exception.CustomException;
import com.yh.reggie.mapper.SetmealMapper;
import com.yh.reggie.pojo.Setmeal;
import com.yh.reggie.pojo.SetmealDish;
import com.yh.reggie.pojo.dto.SetmealDto;
import com.yh.reggie.service.SetmealDishService;
import com.yh.reggie.service.SetmealService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yu
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2022-12-28 12:50:07
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public boolean saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息
        boolean save = this.save(setmealDto);
        //保存套餐与菜品的关联关系
        save = setmealDishService.saveBatch(setmealDto.getSetmealDishes()
                                                    .stream()
                                                    .peek(setmealDish -> {
                                                        setmealDish.setSetmealId
                                                                           (setmealDto.getId().toString());
                                                        setmealDish.setId(null);
                                                    })
                                                    .collect(Collectors.toList()));
        return save;
    }

    @Override
    public boolean removeWithDish(List<Long> ids,boolean flag) {
        //查询套餐状态
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids)
                .eq(Setmeal::getStatus, 1);
        if (this.count(wrapper) > 0 && flag) {
            //如果有正在售卖的就不能删除
            throw new CustomException("有套餐正在售卖，不能删除");
        }
        //可以删除
        boolean remove = this.removeByIds(ids);
        //删除关系表数据
        remove = setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().in(SetmealDish::getSetmealId,ids));
        return remove;
    }

    @Override
    public boolean updateWithDish(SetmealDto setmealDto) {
        this.removeWithDish(Collections.singletonList(setmealDto.getId()),false);
        setmealDto.setId(null);
        return this.saveWithDish(setmealDto);
    }

    @Override
    public SetmealDto getByIdWithDish(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(
                setmealDishService.list(
                new LambdaQueryWrapper<SetmealDish>()
                        .eq(SetmealDish::getSetmealId,setmeal.getId())
                                       )
                                   );
        return setmealDto;
    }
}




