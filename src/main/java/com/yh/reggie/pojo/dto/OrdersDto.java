package com.yh.reggie.pojo.dto;

import com.yh.reggie.pojo.OrderDetail;
import com.yh.reggie.pojo.Orders;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;
	
}