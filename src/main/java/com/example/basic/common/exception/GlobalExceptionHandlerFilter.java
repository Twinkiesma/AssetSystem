package com.example.basic.common.exception;

import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerFilter  {

    @ExceptionHandler(ServiceException.class) // 如果抛出的异常是ServiceException，则调用该方法
    @ResponseBody // 返回json数据
    public Result serviceHandler(ServiceException e){
        // 打印异常信息
        log.error("出现了异常！ {}", e);
        // 从异常对象中获取提示信息封装返回
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class) // 抛出未知异常
    @ResponseBody
    public Result exceptionHandler(Exception e){
        // 打印异常信息
        log.error("出现了未知异常！ {}", e);
        // 从异常对象中获取提示信息封装返回
        return Result.error(Constants.CODE_500, e.getMessage());
    }

}

