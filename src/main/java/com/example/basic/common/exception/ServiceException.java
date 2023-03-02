package com.example.basic.common.exception;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}
