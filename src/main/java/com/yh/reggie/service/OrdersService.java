package com.yh.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.reggie.pojo.dto.OrdersDto;

import org.springframework.transaction.annotation.Transactional;

/**
* @author yu
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-12-29 11:36:08
*/
public interface OrdersService extends IService<Orders> {
    /**
     * 用户提交
     * @param orders 实体类
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean submit(Orders orders);

    Page<OrdersDto> pageByDto(Page<Orders> page, LambdaQueryWrapper<Orders> wrapper);
}
