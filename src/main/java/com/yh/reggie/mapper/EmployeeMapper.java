package com.yh.reggie.mapper;

import com.yh.reggie.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-12-25 19:27:43
* @Entity com.yh.reggie.pojo.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




