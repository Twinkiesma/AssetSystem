package com.example.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("department")
@ApiModel(value = "Department对象", description = "部门信息表")
public class Department {

    @ApiModelProperty("部门ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("部门名称")
    private String deptname;

    @ApiModelProperty("部门状态")
    private String state;

}
