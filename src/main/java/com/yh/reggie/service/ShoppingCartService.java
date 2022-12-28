package com.yh.reggie.service;

import com.yh.reggie.pojo.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yu
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2022-12-28 20:37:44
*/
public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * 修改 购物车中信息
     * @param shoppingCart
     * @param flag 如果是true就是添加 如果是false就是减少
     * @return
     */
    ShoppingCart updateByDishIdOrSetmealId(ShoppingCart shoppingCart,boolean flag);
}
