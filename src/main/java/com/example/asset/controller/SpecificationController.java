package com.example.asset.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.asset.entity.Asset;
import com.example.asset.entity.Specification;
import com.example.asset.service.IAssetService;
import com.example.asset.service.ISpecificationService;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "规格管理")
@RestController
@RequestMapping("/spec")
public class SpecificationController {

    @Resource
    private ISpecificationService specService;

    @Resource
    private IAssetService assetService;

    @ApiOperation("新增或更新规格信息")
    @PostMapping("/save")
    public Result save(@RequestBody Specification spec) {
        specService.saveOrUpdate(spec);
        return Result.success();
    }

    @ApiOperation("删除单个规格信息")
    @ApiImplicitParam(paramType="path", name="specId", value="规格ID", dataType="Integer")
    @DeleteMapping("/delete/{specId}")
    public Result delete(@PathVariable Integer specId) {
        QueryWrapper<Asset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spec_id", specId);
        long count = assetService.count(queryWrapper);
        if(count > 0) {
            throw new ServiceException(Constants.CODE_600,"不能删除正在使用的规格!");
        }
        specService.removeById(specId);
        return Result.success();
    }

    @ApiOperation(value = "启用的品牌信息列表", notes = "用于选择品牌信息下拉框")
    @ApiImplicitParam(paramType="query", name="sortId", value="品类ID", dataType="Integer")
    @GetMapping("/brands")
    public Result getBrands(@RequestParam(value="sortId") Integer sortId) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sort_id", sortId);
        queryWrapper.eq("spec_state", "启用");
        queryWrapper.select("distinct brand");
        return Result.success(specService.list(queryWrapper));
    }

    @ApiOperation(value = "启用的型号信息列表", notes = "用于选择型号信息下拉框")
    @ApiImplicitParam(paramType="query", name="brand", value="品牌", dataType="String")
    @GetMapping("/models")
    public Result getModels(@RequestParam(value="brand") String brand) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand", brand);
        queryWrapper.eq("spec_state", "启用");
        return Result.success(specService.list(queryWrapper));
    }

}
