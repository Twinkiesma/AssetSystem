package com.example.asset.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ApiModel(value = "资产入库信息", description = "接收资产入库信息")
public class WareDTO {

    @ApiModelProperty("资产名称")
    private String assetname;

    @ApiModelProperty("资产规格ID")
    private Integer specId;

    @ApiModelProperty("资产价格")
    private BigDecimal price;

    @ApiModelProperty("入库数量")
    private Integer amount;

}
