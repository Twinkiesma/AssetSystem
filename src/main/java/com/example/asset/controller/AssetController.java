package com.example.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.asset.dto.WareDTO;
import com.example.asset.service.IAssetService;
import com.example.asset.vo.AssetVo;
import com.example.asset.vo.BorrowVo;
import com.example.basic.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "资产管理")
@RestController
@RequestMapping("/asset")
public class AssetController {

    @Resource
    private IAssetService assetService;

    @ApiOperation("资产档案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="sortname", value="品类", dataType="String"),
            @ApiImplicitParam(paramType="query", name="brand", value="品牌", dataType="String"),
            @ApiImplicitParam(paramType="query", name="state", value="资产状态", dataType="String"),
            @ApiImplicitParam(paramType="query", name="name", value="责任人", dataType="String"),
            @ApiImplicitParam(paramType="query", name="position", value="存放地点", dataType="String"),
            @ApiImplicitParam(paramType="query", name="date", value="日期", dataType="String"),
            @ApiImplicitParam(paramType="query", name="deptname", value="使用部门", dataType="String"),
            // 我的资产信息显示
            @ApiImplicitParam(paramType="query", name="userId", value="申请人ID", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="assetname", value="资产名称", dataType="String")
    })
    @GetMapping("/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="sortname", required=false) String sortname, @RequestParam(value="brand", required=false) String brand,
                           @RequestParam(value="state", required=false) String state, @RequestParam(value="name", required=false) String name,
                           @RequestParam(value="position", required=false) String position, @RequestParam(value="date", required=false) String date,
                           @RequestParam(value="deptname", required=false) String deptname,
                           @RequestParam(value="userId", required=false) Integer userId, @RequestParam(value="assetname", required=false) String assetname) {
        Page<AssetVo> page = assetService.findPage(new Page<>(pageNum, pageSize), sortname, brand, state, name, position, date, deptname, userId, assetname);
        return Result.success(page);
    }

    @ApiOperation("资产入库")
    @PostMapping("/ware")
    public Result save(@RequestBody WareDTO wareDTO) {
        assetService.saveWare(wareDTO);
        return Result.success();
    }

    @ApiOperation(value = "资产信息显示", notes = "选择资产弹窗显示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="assetname", value="资产名称", dataType="String"),
            @ApiImplicitParam(paramType="query", name="sortname", value="品类名称", dataType="String"),
            @ApiImplicitParam(paramType="query", name="brand", value="品牌", dataType="String")
    })
    @GetMapping("/borrow/page")
    public Result findBorrowPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                                 @RequestParam(value="assetname", required=false) String assetname, @RequestParam(value="sortname", required=false) String sortname,
                                 @RequestParam(value="brand", required=false) String brand) {
        Page<BorrowVo> page = assetService.findBorrowPage(new Page<>(pageNum, pageSize), assetname, sortname, brand);
        return Result.success(page);
    }

    @ApiOperation("资产已修复")
    @ApiImplicitParam(paramType="path", name="id", value="资产ID", dataType="Integer")
    @PostMapping("/restore/{id}")
    public Result restore(@PathVariable Integer id) {
        assetService.restore(id);
        return Result.success();
    }

    @ApiOperation("资产已报废")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="id", value="资产ID", dataType="Integer"),
    })
    @PostMapping("/discard/{id}")
    public Result discard(@PathVariable Integer id) {
        assetService.discard(id);
        return Result.success();
    }

}
