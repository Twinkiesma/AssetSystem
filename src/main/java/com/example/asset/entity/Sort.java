package com.example.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sort")
@ApiModel(value = "资产品类", description = "资产品类表")
public class Sort {

    @ApiModelProperty("品类ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("品类名称")
    private String sortname;

    @ApiModelProperty("品类状态")
    private String state;

}
