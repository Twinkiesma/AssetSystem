package com.example.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.service.IRoleMenuService;
import com.example.data.service.IRoleService;
import com.example.data.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserService userService;

    @Resource
    private IRoleMenuService roleMenuService;

    @ApiOperation(value = "角色信息列表", notes = "条件分页查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="state", value="角色状态", dataType="String")
    })
    @GetMapping("/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="state", required=false) String state) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("state", state);
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @ApiOperation("新增或更新角色信息列表")
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @ApiOperation("删除单个角色信息")
    @ApiImplicitParam(paramType="path", name="id", value="角色ID", dataType="Integer")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        long count = userService.count(queryWrapper);
        if(count > 0) {
            throw new ServiceException(Constants.CODE_600,"不能删除正在使用的角色!");
        }
        roleService.removeRole(id);
        return Result.success();
    }

    @ApiOperation(value = "权限分配信息列表", notes = "根据角色ID绑定角色与菜单关系")
    @ApiImplicitParam(paramType="path", name="roleId", value="角色ID", dataType="Integer")
    @PostMapping("/purview/save/{roleId}")
    public Result purview(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleMenuService.savePurview(roleId, menuIds);
        return Result.success();
    }

    @ApiOperation(value = "角色权限信息列表", notes = "根据角色ID查询角色拥有的菜单信息列表")
    @ApiImplicitParam(paramType="path", name="roleId", value="角色ID", dataType="Integer")
    @GetMapping("/purview/page/{roleId}")
    public Result findPurview(@PathVariable Integer roleId) {
        return Result.success(roleMenuService.getPurview(roleId));
    }

    // 查询启用的角色
    @ApiOperation(value = "启用的角色信息列表", notes = "用于选择角色信息下拉框")
    @GetMapping
    public Result findRoles() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "启用");
        return Result.success(roleService.list(queryWrapper));
    }

}

