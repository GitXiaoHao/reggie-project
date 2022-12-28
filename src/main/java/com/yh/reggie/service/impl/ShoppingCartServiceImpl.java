package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.common.BaseContext;
import com.yh.reggie.pojo.ShoppingCart;
import com.yh.reggie.service.ShoppingCartService;
import com.yh.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-12-28 20:37:44
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

    @Override
    public ShoppingCart updateByDishIdOrSetmealId(ShoppingCart shoppingCart, boolean flag) {
        //查询用户的信息
        Object userId = BaseContext.getCurrent();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
                .eq(ShoppingCart::getUserId, userId);
        ShoppingCart cart = this.getOne(wrapper);
        //判断是否为空 如果为空就是添加
        if(cart == null){
            cart = shoppingCart;
            cart.setNumber(1);
        }else{
            //不为空 判断是添加还是减少
            if(flag){
                //添加
                cart.setNumber(cart.getNumber() + 1);
            }else {
                if(cart.getNumber() <= 1 || cart.getNumber() == null){
                   this.removeById(cart.getId());
                   return null;
                }
                cart.setNumber(cart.getNumber() - 1);
            }
        }
        this.saveOrUpdate(cart);
        return cart;
    }
}




