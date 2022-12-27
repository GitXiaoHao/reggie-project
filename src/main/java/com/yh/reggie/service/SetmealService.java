package com.yh.reggie.service;

import com.yh.reggie.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.reggie.pojo.dto.SetmealDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author yu
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-12-28 12:50:07
*/
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐与菜品的关联关系
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐 也删除套餐与菜品关系的表
     * @param ids 根据id删除
     * @param flag 判断是否要根据售卖情况删除 如果为true就是要根据售卖情况删除
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean removeWithDish(List<Long> ids,boolean flag);

    /**
     * 修改套餐。 也修改菜品对应的值
     * @param setmealDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateWithDish(SetmealDto setmealDto);

    /**
     * 根据id查询套餐 并且查询菜品
     * @param id
     * @return
     */
    SetmealDto getByIdWithDish(Long id);
}
