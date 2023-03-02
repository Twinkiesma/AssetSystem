package com.example.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("specification")
@ApiModel(value = "资产规格", description = "资产规格表")
public class Specification {

    @ApiModelProperty("规格ID")
      @TableId(value = "spec_id", type = IdType.AUTO)
    private Integer specId;

    @ApiModelProperty("品类ID")
    private Integer sortId;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("规格状态")
    private String specState;

}
