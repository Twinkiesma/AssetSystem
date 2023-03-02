package com.example.asset.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel(value = "资产品类-规格信息", description = "资产品类页面")
public class SortVo {

    @ApiModelProperty("资产品类ID")
    private Integer id;

    @ApiModelProperty("品类名称")
    private String sortname;

    @ApiModelProperty("品类状态")
    private String state;

    @ApiModelProperty("规格ID")
    private Integer specId;

    @ApiModelProperty("规格中品类ID")
    private Integer sortId;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("规格状态")
    private String specState;

    @ApiModelProperty("树结构")
    private List<SortVo> children;

}
