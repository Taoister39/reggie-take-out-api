package com.reggie.reggietakeoutapi.handler;

import com.reggie.reggietakeoutapi.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


// 拦截控制器
@ControllerAdvice
@ResponseBody
public class AllExceptionHandler {
    // 只处理异常控制器
    @ExceptionHandler
    public Result<String> doException(Exception exception) {
        exception.printStackTrace();
        return Result.error("系统异常");
    }
}
