package com.reggie.reggietakeoutapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.reggietakeoutapi.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
