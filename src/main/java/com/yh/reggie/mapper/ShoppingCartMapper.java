package com.yh.reggie.mapper;

import com.yh.reggie.pojo.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-12-28 20:37:44
* @Entity com.yh.reggie.pojo.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




