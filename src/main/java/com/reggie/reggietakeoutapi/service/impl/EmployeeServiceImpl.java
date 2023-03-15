package com.reggie.reggietakeoutapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.reggietakeoutapi.dao.entity.Employee;
import com.reggie.reggietakeoutapi.dao.mapper.EmployeeMapper;
import com.reggie.reggietakeoutapi.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
