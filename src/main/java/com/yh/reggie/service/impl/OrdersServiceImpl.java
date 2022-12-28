package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.controller.exception.CustomException;
import com.yh.reggie.mapper.OrdersMapper;
import com.yh.reggie.pojo.AddressBook;
import com.yh.reggie.pojo.OrderDetail;
import com.yh.reggie.pojo.Orders;
import com.yh.reggie.pojo.ShoppingCart;
import com.yh.reggie.pojo.User;
import com.yh.reggie.pojo.dto.OrdersDto;
import com.yh.reggie.service.AddressBookService;
import com.yh.reggie.service.OrderDetailService;
import com.yh.reggie.service.OrdersService;
import com.yh.reggie.service.ShoppingCartService;
import com.yh.reggie.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author yu
 * @description 针对表【orders(订单表)】的数据库操作Service实现
 * @createDate 2022-12-29 11:36:08
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Override
    public boolean submit(Orders orders) {
        //当前用户 根据id查地址
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new CustomException("地址为空，请先添加地址信息");
        }
        User user = userService.getById(addressBook.getUserId());
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<ShoppingCart>()
                                                      .eq(ShoppingCart::getUserId,
                                                              addressBook.getUserId());
        //根据userId查购物车数据
        List<ShoppingCart> shoppingCartList = shoppingCartService.list();
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车为空");
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        String orderId = IdWorker.getIdStr();
        List<OrderDetail> orderDetailList = shoppingCartList.stream()
                                            .map(item -> {
                                                OrderDetail orderDetail = new OrderDetail();
                                                orderDetail.setOrderId(Long.valueOf(orderId));
                                                orderDetail.setNumber(item.getNumber());
                                                orderDetail.setDishFlavor(item.getDishFlavor());
                                                orderDetail.setDishId(item.getDishId());
                                                orderDetail.setSetmealId(item.getSetmealId());
                                                orderDetail.setName(item.getName());
                                                orderDetail.setImage(item.getImage());
                                                orderDetail.setAmount(item.getAmount());
                                                atomicInteger.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
                                                return orderDetail;
                                            }).collect(Collectors.toList());
        //设置值
        orders.setNumber(orderId);
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                                  + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                                  + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                                  + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(4);
        orders.setUserName(addressBook.getConsignee());
        orders.setUserId(user.getId());
        orders.setAmount(new BigDecimal(atomicInteger.get()));
        orderDetailService.saveBatch(orderDetailList);
        this.save(orders);
        //删除购物车
        shoppingCartService.remove(wrapper);
        return true;
    }

    @Override
    public Page<OrdersDto> pageByDto(Page<Orders> page, LambdaQueryWrapper<Orders> wrapper) {
        this.page(page,wrapper);
        List<OrdersDto> ordersDtoList = page.getRecords().stream()
                                            .map(orders -> {
                                                OrdersDto ordersDto = new OrdersDto();
                                                BeanUtils.copyProperties(orders,ordersDto);
                                                LambdaQueryWrapper<OrderDetail> queryWrapper =
                                                        new LambdaQueryWrapper<>();
                                                queryWrapper.eq(OrderDetail::getOrderId,orders.getNumber());
                                                List<OrderDetail> detailList = orderDetailService.list(queryWrapper);
                                                ordersDto.setOrderDetails(detailList);
                                                return ordersDto;
                                            }).collect(Collectors.toList());
        Page<OrdersDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(page,dtoPage,"records");
        dtoPage.setRecords(ordersDtoList);
        return dtoPage;
    }
}




