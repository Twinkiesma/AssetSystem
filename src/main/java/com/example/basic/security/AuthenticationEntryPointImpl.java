package com.example.basic.security;

import com.alibaba.fastjson.JSON;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 打印异常信息
        authException.printStackTrace();
        // 响应给前端
        Result result = Result.error(Constants.CODE_300, authException.getMessage());
        WebUtils.renderString(response, JSON.toJSONString(result));
    }

}


