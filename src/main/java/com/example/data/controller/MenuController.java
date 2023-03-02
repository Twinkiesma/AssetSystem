package com.example.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import com.example.data.entity.Dict;
import com.example.data.entity.Menu;
import com.example.data.service.IDictService;
import com.example.data.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private IDictService dictService;

    // 显示菜单列表
    @ApiOperation(value = "菜单信息列表", notes = "菜单树")
    @GetMapping("/page")
    public Result findAll() {
        return Result.success(menuService.findMenus());
    }

    // 新增或更新
    @ApiOperation("新增或更新菜单信息")
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @ApiOperation("删除单个菜单信息")
    @ApiImplicitParam(paramType="path", name="id", value="菜单ID", dataType="Integer")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        long count = menuService.count(queryWrapper);
        if(count > 0){
            throw new ServiceException(Constants.CODE_600, "您删除的菜单下有子菜单，请先删除子菜单！");
        }
        menuService.removeMenu(id);
        return Result.success();
    }

    @ApiOperation(value = "图标信息列表", notes = "用于选择菜单图标下拉框")
    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "icon");
        return Result.success(dictService.list(queryWrapper));
    }

}

