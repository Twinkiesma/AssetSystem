package com.example.asset.vo;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "申请-审核信息", description = "查看申请页面")
public class AuditVo {

    @ApiModelProperty("申请单ID")
    private Integer id;

    @ApiModelProperty("申请类型")
    private String type;

    @ApiModelProperty("申请人")
    private String name;

    @ApiModelProperty("申请部门")
    private String deptname;

    @ApiModelProperty("申请日期")
    private String date;

    @ApiModelProperty("申请资产信息")
    @TableField(value = "assets", typeHandler = JacksonTypeHandler.class)
    private JSONArray assets;

    @ApiModelProperty("申请单状态")
    private String state;

    @ApiModelProperty("审核记录ID")
    private Integer auId;

    @ApiModelProperty("审核人")
    private String auName;

    @ApiModelProperty("审核日期")
    private String auDate;

    @ApiModelProperty("审核结果")
    private String result;
}
