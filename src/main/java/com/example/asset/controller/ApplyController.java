package com.example.asset.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.asset.entity.Apply;
import com.example.asset.service.IApplyService;
import com.example.basic.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "申请管理")
@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Resource
    private IApplyService applyService;

    @ApiOperation("新增申请单")
    @PostMapping("/save")
    public Result save(@RequestBody Apply apply) {
        applyService.save(apply);
        return Result.success();
    }

    @ApiOperation(value = "申请单信息", notes = "显示登录用户特定申请单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="userId", value="申请人ID", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="type", value="申请类型", dataType="String"),
            @ApiImplicitParam(paramType="query", name="date", value="申请日期", dataType="String"),
            @ApiImplicitParam(paramType="query", name="state", value="部门状态", dataType="String"),
    })
    @GetMapping("/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="userId") Integer userId, @RequestParam(value="type") String type,
                           @RequestParam(value="date", required=false) String date,
                           @RequestParam(value="state", required=false) String state) {
        QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("type", type);
        queryWrapper.like("date", date);
        queryWrapper.like("state", state);
        return Result.success(applyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

