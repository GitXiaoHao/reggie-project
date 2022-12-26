package com.yh.reggie;

import com.yh.reggie.pojo.DishFlavor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReggieProjectApplicationTests {

    @Test
    void contextLoads() {
        //获得口味 已在controller判断是否为空
        List<DishFlavor> flavors = new ArrayList<>();
        Long dishDtoId = 0L;
        //使用流设置菜品id
        flavors = flavors.stream()
                          .peek(flavor -> flavor.setDishId(dishDtoId))
                          .collect(Collectors.toList());
    }

}
