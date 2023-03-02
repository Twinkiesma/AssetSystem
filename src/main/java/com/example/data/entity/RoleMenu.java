package com.example.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("role_menu")
@ApiModel(value = "RoleMenu对象", description = "角色-菜单关系表")
public class RoleMenu {

      @ApiModelProperty("角色ID")
      private Integer roleId;

      @ApiModelProperty("菜单ID")
      private Integer menuId;

}
