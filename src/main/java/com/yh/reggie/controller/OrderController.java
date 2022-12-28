package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.common.BaseContext;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Orders;
import com.yh.reggie.pojo.dto.OrdersDto;
import com.yh.reggie.service.OrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/29
 *
 * @author yu
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        orders.setOrderTime(LocalDateTime.now());
        orders.setStatus(1);
        return ordersService.submit(orders) ? Result.success("提交成功") : Result.error("提交失败");
    }
    @GetMapping("/userPage")
    public Result<Page<OrdersDto>> userPage(Page<Orders> page){
        Object userId = BaseContext.getCurrent();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, userId);
        return Result.success(ordersService.pageByDto(page, wrapper));
    }

    @GetMapping("/page")
    public Result<Page<OrdersDto>> page(int page ,int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Orders::getOrderTime)
                .like(number != null, Orders::getNumber, number)
                .between(beginTime != null && endTime != null, Orders::getOrderTime, beginTime, endTime);
        return Result.success(ordersService.pageByDto(pageInfo, wrapper));
    }
}
