package com.example.basic.security;

import com.alibaba.fastjson.JSON;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 打印异常信息
        accessDeniedException.printStackTrace();
        // 响应给前端
        Result result = Result.error(Constants.CODE_301, accessDeniedException.getMessage());
        WebUtils.renderString(response, JSON.toJSONString(result));
    }

}
