package com.reggie.reggietakeoutapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.reggietakeoutapi.dao.entity.User;
import com.reggie.reggietakeoutapi.dao.mapper.UserMapper;
import com.reggie.reggietakeoutapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
