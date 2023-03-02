package com.example.asset.entity;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "apply", autoResultMap = true)
@ApiModel(value = "申请信息", description = "申请信息表")
public class Apply {

    @ApiModelProperty("申请单ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("申请人ID")
    private Integer userId;

    @ApiModelProperty("申请日期")
    private String date;

    @ApiModelProperty("申请资产信息")
    @TableField(value = "assets", typeHandler = JacksonTypeHandler.class)
    private JSONArray assets;

    @ApiModelProperty("申请类型")
    private String type;

    @ApiModelProperty("申请单状态")
    private String state;

}
