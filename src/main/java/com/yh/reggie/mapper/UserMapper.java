package com.yh.reggie.mapper;

import com.yh.reggie.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-12-28 17:41:56
* @Entity com.yh.reggie.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




