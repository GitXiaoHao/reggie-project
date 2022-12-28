package com.yh.reggie;

import com.yh.reggie.pojo.DishFlavor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void setRedisTemplate(){
        try {
            redisTemplate.opsForValue().set("city", "北京");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通用操作
     */
    @Test
   public void testCommon(){
        //获取所有的key
        Set keys = redisTemplate.keys("*");
        //判断key是否存在
        Boolean city = redisTemplate.hasKey("city");
        //删除key
        Boolean city1 = redisTemplate.delete("city");
        //获取key对应的value的数据类型
        DataType city2 = redisTemplate.type("city");

    }
}
