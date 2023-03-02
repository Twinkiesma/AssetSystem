package com.example.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("asset")
@ApiModel(value = "资产信息", description = "资产信息表")
public class Asset {

    @ApiModelProperty("资产ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资产编号")
    private String number;

    @ApiModelProperty("资产名称")
    private String assetname;

    @ApiModelProperty("规格ID")
    private Integer specId;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("资产状态")
    private String state;

    @ApiModelProperty("维修/报废原因")
    private String reason;

    @ApiModelProperty("存放位置")
    private String position;

    @ApiModelProperty("责任人ID")
    private Integer userId;

    @ApiModelProperty("领用/报废日期")
    private String date;

}
