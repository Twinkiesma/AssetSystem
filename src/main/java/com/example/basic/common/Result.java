package com.example.basic.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;    // 状态码
    private String msg;     // 提示信息
    private Object data;    // 携带的数据

    // 返回成功
    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    // 返回成功携带数据
    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    // 返回失败并提示
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

}
