package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.DishFlavor;
import com.yh.reggie.service.DishFlavorService;
import com.yh.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-12-27 20:53:46
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




