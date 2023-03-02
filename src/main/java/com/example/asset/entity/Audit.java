package com.example.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("audit")
@ApiModel(value = "审核信息", description = "审核信息表")
public class Audit {

      @ApiModelProperty("审核记录ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("申请单ID")
      private Integer apId;

      @ApiModelProperty("审核人ID")
      private Integer auUserId;

      @ApiModelProperty("审核日期")
      private String auDate;

      @ApiModelProperty("审核结果")
      private String result;

}
