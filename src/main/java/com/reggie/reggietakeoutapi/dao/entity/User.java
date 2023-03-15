package com.reggie.reggietakeoutapi.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String phone;
    private String idNumber;
    private String avatar;
    private Integer status; // 是否禁用
}
