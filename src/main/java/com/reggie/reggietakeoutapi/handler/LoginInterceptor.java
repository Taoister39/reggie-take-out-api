package com.reggie.reggietakeoutapi.handler;

import com.alibaba.fastjson2.JSON;
import com.reggie.reggietakeoutapi.service.UserService;
import com.reggie.reggietakeoutapi.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
//    @Autowired
//    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求是否路径是Controller方法
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        Long employeeId = (Long) request.getSession().getAttribute("employee");
        if(employeeId != null){
            return true;
        }

        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return false;
    }
}
