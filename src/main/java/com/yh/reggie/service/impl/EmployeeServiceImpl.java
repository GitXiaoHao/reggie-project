package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.Employee;
import com.yh.reggie.service.EmployeeService;
import com.yh.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-12-25 19:27:43
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




