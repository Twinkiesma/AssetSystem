package com.example.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ApiModel(value = "资产信息", description = "资产档案页面")
public class AssetVo {

    @ApiModelProperty("资产ID")
    private Integer id;

    @ApiModelProperty("编号")
    private String number;

    @ApiModelProperty("名称")
    private String assetname;

    @ApiModelProperty("品类")
    private String sortname;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("资产价格")
    private BigDecimal price;

    @ApiModelProperty("资产状态")
    private String state;

    @ApiModelProperty("存放地点")
    private String position;

    @ApiModelProperty("责任人")
    private String name;

    @ApiModelProperty("借用/维修/报废日期")
    private String date;

    @ApiModelProperty("使用部门")
    private String deptname;

    @ApiModelProperty("维修/报废原因")
    private String reason;

}
