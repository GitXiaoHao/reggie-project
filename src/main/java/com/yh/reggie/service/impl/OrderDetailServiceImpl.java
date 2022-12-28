package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.OrderDetail;
import com.yh.reggie.service.OrderDetailService;
import com.yh.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-12-29 11:36:19
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




