package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.common.BaseContext;
import com.yh.reggie.common.Information;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Employee;
import com.yh.reggie.service.EmployeeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

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
    private final String userInfo = Information.USER_INFO;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆
     *
     * @param request       将用户存入session域里
     * @param employee      员工信息
     * @param bindingResult 字段校验
     * @return 结果集
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody @Valid Employee employee,
                                  BindingResult bindingResult) {
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
        BaseContext.setCurrent(employeeServiceOne);
        return Result.success(employeeServiceOne);
    }

    /**
     * 退出系统
     *
     * @return 不需返回
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //清理session中的信息
        request.getSession().removeAttribute(userInfo);
        BaseContext.remove();
        return Result.success("退出成功");
    }

    /**
     * 信息分页查询
     *
     * @param page     当前多少页
     * @param pageSize 当前页显示的条数
     * @param name     用户查询的姓名
     * @return　 泛型为内置分页类
     */
    @GetMapping("/page")
    public Result<Page<Employee>> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //使用条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name)
                //添加排序条件
                .orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 新增员工
     *
     * @param employee 员工信息
     * @return 成功或者失败
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        //设置初始密码 进行MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex(Information.INITIAL_PASSWORD.getBytes()));
        ///使用了公共字段自动填充
        //设置创建时间
        //employee.setCreateTime(LocalDateTime.now());
        ////创建修改时间
        //employee.setUpdateTime(LocalDateTime.now());
        ////设置创建人
        //Employee user = (Employee) request.getSession().getAttribute(Information.USER_INFO);
        //employee.setCreateUser(user.getId());
        //employee.setUpdateUser(user.getId());
        boolean save = employeeService.save(employee);
        if (save) {
            return Result.success("新增员工成功");
        }
        return Result.error("新增失败");
    }

    /**
     * 修改员工信息
     *
     * @param employee 需要修改的员工
     * @return 成功或失败
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        ///使用了公共字段自动填充
        ////修改更新时间和更新人
        //employee.setUpdateTime(LocalDateTime.now());
        //Employee employeeUser = (Employee) request.getSession().getAttribute(userInfo);
        //employee.setUpdateUser(employeeUser.getId());
        //执行修改操作
        boolean update = employeeService.updateById(employee);
        return update ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return 员工信息
     */
    @GetMapping("/{id}")
    private Result<Employee> getById(@PathVariable Long id) {
        Optional<Long> optional = Optional.ofNullable(id);
        Employee employee = employeeService.getById(optional.orElse(0L));
        return employee == null ? Result.error("没有查询出结果") : Result.success(employee);
    }
}
