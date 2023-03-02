package com.example.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.asset.entity.Audit;
import com.example.asset.service.IApplyService;
import com.example.asset.service.IAuditService;
import com.example.asset.vo.AuditVo;
import com.example.basic.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "审核管理")
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Resource
    private IAuditService auditService;

    @Resource
    private IApplyService applyService;

    @ApiOperation("新增审核记录")
    @PostMapping("/save")
    public Result save(@RequestBody Audit audit) {
        auditService.save(audit);
        return Result.success();
    }

    @ApiOperation(value = "申请单信息/审核记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="date", value="申请日期", dataType="String"),
            @ApiImplicitParam(paramType="query", name="type", value="申请类型", dataType="String"),
            @ApiImplicitParam(paramType="query", name="name", value="申请人", dataType="String"),
            @ApiImplicitParam(paramType="query", name="userId", value="申请人ID", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="deptname", value="申请部门", dataType="String"),
            @ApiImplicitParam(paramType="query", name="state", value="申请单状态", dataType="String"),
            @ApiImplicitParam(paramType="query", name="auDate", value="审核日期", dataType="String"),
            @ApiImplicitParam(paramType="query", name="auName", value="审核人", dataType="String"),
            @ApiImplicitParam(paramType="query", name="result", value="审核结果", dataType="String")

    })
    @GetMapping("/apply/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="date", required=false) String date, @RequestParam(value="type", required=false) String type,
                           @RequestParam(value="name", required=false) String name, @RequestParam(value="userId", required=false) Integer userId,
                           @RequestParam(value="deptname", required=false) String deptname, @RequestParam(value="state", required=false) String state,
                           @RequestParam(value="auDate", required=false) String auDate, @RequestParam(value="auName", required=false) String auName,
                           @RequestParam(value="result", required=false) String result) {
        Page<AuditVo> page = applyService.findPage(new Page<>(pageNum, pageSize), date, type, name, userId, deptname, state, auDate, auName, result);
        return Result.success(page);
    }

}

