package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yh.reggie.common.Information;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Employee;
import com.yh.reggie.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/25
 *
 * @author yu
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private String userInfo = Information.USER_INFO;
    /**
     * 员工登陆
     * @param request 将用户存入session域里
     * @param employee 员工信息
     * @param bindingResult 字段校验
     * @return 结果集
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody @Valid Employee employee, BindingResult bindingResult){
        //检查所有字段是否验证通过
        if (bindingResult.hasErrors()) {
            //验证失败，返回第一条错误信息
            return Result.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //使用wrapper进行查询
        employeeLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        //在注册用户名的时候字段不能重复
        Employee employeeServiceOne = employeeService.getOne(employeeLambdaQueryWrapper);
        //将页面提交的密码password进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //如果没有查询到则返回登陆失败结果
        //密码比对，如果不一致则返回登陆失败结果
        if (employeeServiceOne == null || !employeeServiceOne.getPassword().equals(password)) {
            return Result.error("密码或用户名错误");
        }
        //比对成功
        //查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (employeeServiceOne.getStatus() == 0) {
            //禁用
            return Result.error("账号已禁用");
        }
        //登陆成功，将员工id存入session并返回登陆成功结果
        request.getSession().setAttribute(userInfo, employeeServiceOne);
        return Result.success(employeeServiceOne);
    }

    /**
     * 退出系统
     * @return 不需返回
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //清理session中的信息
        request.getSession().removeAttribute(userInfo);
        return Result.success("退出成功");
    }

    @GetMapping("/page")
    public Result<String> page(HttpServletRequest request){

        return null;
    }

    /**
     * 新增员工
     * @param employee 员工信息
     * @return 成功或者失败
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee){
        //设置初始密码 进行MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex(Information.INITIAL_PASSWORD.getBytes()));
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //创建修改时间
        employee.setUpdateTime(LocalDateTime.now());
        //设置创建人
        Employee user = (Employee) request.getSession().getAttribute(Information.USER_INFO);
        employee.setCreateUser(user.getId());
        employee.setUpdateUser(user.getId());
        boolean save = employeeService.save(employee);
        if (save) {
            return Result.success("新增员工成功");
        }
        return Result.error("新增失败");
    }
}
