package com.example.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收前端登录请求的参数
 */
@Data
@ApiModel(value = "UserDTO对象", description = "接收登录信息")
public class UserDTO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

}
