package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.User;
import com.yh.reggie.service.UserService;
import com.yh.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-12-28 17:41:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




