package com.reggie.reggietakeoutapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 状态码，1或0

    private String msg;// 状态信息

    public T data; // 数据
    private Map map;// 动态数据

    public static Result error(String message) {
        return new Result(0, message, null, new HashMap());
    }
    public static <T> Result success(T data){
        return new Result(1,"成功",data,new HashMap());
    }
    public Result add(String key,Object value){
        this.map.put(key,value);
        return this;
    }

}
