package com.example.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import com.example.data.entity.Department;
import com.example.data.entity.User;
import com.example.data.service.IDepartmentService;
import com.example.data.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private IDepartmentService departmentService;

    @Resource
    private IUserService userService;

    @ApiOperation(value = "部门信息列表", notes = "条件分页查询部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="state", value="部门状态", dataType="String")
    })
    @GetMapping("/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="state", required=false) String state) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("state", state);
        return Result.success(departmentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @ApiOperation("新增或更新部门信息")
    @PostMapping("/save")
    public Result save(@RequestBody Department department) {
        departmentService.saveOrUpdate(department);
        return Result.success();
    }

    @ApiOperation("删除单个部门信息")
    @ApiImplicitParam(paramType="path", name="id", value="部门ID", dataType="Integer")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", id);
        long count = userService.count(queryWrapper);
        if(count > 0) {
            throw new ServiceException(Constants.CODE_600,"不能删除包含员工的部门!");
        }
        departmentService.removeById(id);
        return Result.success();

    }

    @ApiOperation(value = "启用的部门信息列表", notes = "用于选择部门信息下拉框")
    @GetMapping
    public Result findDepartments() {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "启用");
        return Result.success(departmentService.list(queryWrapper));
    }

}
