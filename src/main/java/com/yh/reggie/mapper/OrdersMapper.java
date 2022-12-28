package com.yh.reggie.mapper;

import com.yh.reggie.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-12-29 11:36:08
* @Entity com.yh.reggie.pojo.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




