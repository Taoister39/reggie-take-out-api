package com.reggie.reggietakeoutapi.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.reggietakeoutapi.dao.entity.Employee;
import com.reggie.reggietakeoutapi.service.impl.EmployeeServiceImpl;
import com.reggie.reggietakeoutapi.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HandshakeCompletedEvent;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * 管理员登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) { // 实际上参数的employee只有密码和用户名
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // 加密

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee resultEmployee = employeeService.getOne(queryWrapper); // 通过查询条件只要一条

        if (resultEmployee == null) {
            return Result.error("登录失败");
        }
        // 核对密码
        if (!resultEmployee.getPassword().equals(password)) {
            return Result.error("登录失败");
        }
        // 禁用的账号不做登录
        if (resultEmployee.getStatus() == 0) {
            return Result.error("账号禁用了");
        }
        // 把id放到session，客户端解析的session可以直接拿来用
        request.getSession().setAttribute("employee", resultEmployee.getId());
        return Result.success(resultEmployee);
    }

    /**
     * 管理员退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 获取员工列表
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        // 分页构造器
        Page<Employee> pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 如果为空不会过滤这个条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    /**
     * 更新员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        System.out.println(employee.toString());


        Long userId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(userId);


        // 改变记录，通过employee的id
        employeeService.updateById(employee);

        return Result.success("员工信息修改成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        // 将传递过来的密码进行md5加密
        String password = "123456"; // 初始密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);
        // 创建和更新的人
        Long userId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(userId);
        employee.setUpdateUser(userId);
        // 查询是否已有用户
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.select(Employee::getUsername);
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());

        if (employeeService.getOne(lambdaQueryWrapper) != null) {
            return Result.error("用户名已使用");
        }
        // 新增一条记录
        if (employeeService.save(employee)) {

            return Result.success("新增员工成功");
        }
        return Result.error("新增员工失败");
    }
}
