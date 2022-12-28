package com.yh.reggie.mapper;

import com.yh.reggie.pojo.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-12-29 11:36:19
* @Entity com.yh.reggie.pojo.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




