package com.example.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UserPasswordDTO对象", description = "接收改密码信息")
public class UserPasswordDTO {

    @ApiModelProperty("原密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("确认密码")
    private String checkPassword;

}
