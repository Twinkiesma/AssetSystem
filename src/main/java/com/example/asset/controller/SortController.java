package com.example.asset.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.asset.entity.Sort;
import com.example.asset.entity.Specification;
import com.example.asset.service.ISortService;
import com.example.asset.service.ISpecificationService;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "品类管理")
@RestController
@RequestMapping("/sort")
public class SortController {

    @Resource
    private ISortService sortService;

    @Resource
    private ISpecificationService specService;

    @ApiOperation(value = "品类规格信息列表", notes = "品类-规格树结构")
    @GetMapping("/page")
    public Result findAll() {
        return Result.success(sortService.findSort());
    }

    @ApiOperation("新增或更新品类信息")
    @PostMapping("/save")
    public Result save(@RequestBody Sort sort) {
        sortService.saveOrUpdate(sort);
        return Result.success();
    }

    @ApiOperation("删除单个品类信息")
    @ApiImplicitParam(paramType="path", name="id", value="品类ID", dataType="Integer")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sort_id", id);
        long count = specService.count(queryWrapper);
        if(count > 0) {
            throw new ServiceException(Constants.CODE_600, "您删除的品类下含有规格，请先删除规格！");
        }
        sortService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "启用的品类信息列表", notes = "用于选择品类信息下拉框")
    @GetMapping("/sorts")
    public Result getSorts() {
        QueryWrapper<Sort> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "启用");
        return Result.success(sortService.list(queryWrapper));
    }

}
