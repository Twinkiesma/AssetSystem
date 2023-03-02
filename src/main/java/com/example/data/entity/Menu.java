package com.example.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@TableName("menu")
@ApiModel(value = "Menu对象", description = "菜单消息表")
public class Menu {

      @ApiModelProperty("菜单ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("菜单名称")
      private String name;

      @ApiModelProperty("路由地址")
      private String path;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("父菜单ID")
      private Integer pid;

      @ApiModelProperty("组件路径")
      private String component;

      @TableField(exist = false)
      @ApiModelProperty("子菜单列表")
      private List<Menu> children;

}
