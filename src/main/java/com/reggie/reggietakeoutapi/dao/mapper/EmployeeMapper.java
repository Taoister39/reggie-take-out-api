package com.reggie.reggietakeoutapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.reggietakeoutapi.dao.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
