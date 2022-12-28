package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yh.reggie.common.BaseContext;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.ShoppingCart;
import com.yh.reggie.service.ShoppingCartService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/28
 *
 * @author yu
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 获取购物车
     * @return
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        //获取用户id
        Long id = (Long) BaseContext.getCurrent();
        //根据id查询购物车
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,id)
                .orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> cartList = shoppingCartService.list(wrapper);
        return Result.success(cartList);
    }
    @PostMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart cart = shoppingCartService.updateByDishIdOrSetmealId(shoppingCart, true);
        return Result.success(cart);
    }

    /**
     * 根据id减少购物车的菜品数量
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        return Result.success(shoppingCartService.updateByDishIdOrSetmealId(shoppingCart,false));
    }
}
