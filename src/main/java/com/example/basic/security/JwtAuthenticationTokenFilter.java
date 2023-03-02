package com.example.basic.security;

import com.alibaba.fastjson.JSON;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.utils.JwtUtil;
import com.example.basic.utils.RedisCache;
import com.example.basic.utils.WebUtils;
import com.example.data.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            // 放行，接下来是做token解析工作，放行给后面的处理器进行异常抛出
            filterChain.doFilter(request,response);
            return;
        }

        // 2.解析token
        Claims claims = null;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            //token超时
            Result result = Result.error(Constants.CODE_300, "token非法，请重新登录！");
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();

        // 3.从redis中获取用户信息
        String redisKey = "login_" + userId;
        User user = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(user)){
            Result result = Result.error(Constants.CODE_300, "用户未登录，请登录！");
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        // 4.存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 5.放行
        filterChain.doFilter(request,response);
    }

}
