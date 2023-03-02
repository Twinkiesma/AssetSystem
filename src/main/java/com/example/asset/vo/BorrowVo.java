package com.example.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "资产信息", description = "资产领用弹框页面")
public class BorrowVo {

    @ApiModelProperty("名称")
    private String assetname;

    @ApiModelProperty("品类")
    private String sortname;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("规格ID")
    private Integer specId;

}
