package com.example.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("role")
@ApiModel(value = "Role对象", description = "角色信息表")
public class Role {

      @ApiModelProperty("角色ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("角色名称")
      private String rolename;

      @ApiModelProperty("角色状态")
      private String state;

}
