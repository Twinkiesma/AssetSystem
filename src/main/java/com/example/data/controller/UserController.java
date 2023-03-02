package com.example.data.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.basic.common.Constants;
import com.example.basic.common.Result;
import com.example.basic.common.exception.ServiceException;
import com.example.data.dto.UserDTO;
import com.example.data.dto.UserPasswordDTO;
import com.example.data.entity.User;
import com.example.data.service.IMenuService;
import com.example.data.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IMenuService menuService;

    // 关联查询-根据角色ID查询角色名称，部门ID查询部门名称
    @ApiOperation(value = "用户信息列表", notes = "条件分页关联查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNum", value="页码", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="pageSize", value="页面条数", dataType="Integer"),
            @ApiImplicitParam(paramType="query", name="username", value="用户名", dataType="String"),
            @ApiImplicitParam(paramType="query", name="state", value="用户状态", dataType="String")
    })
    @GetMapping("/page")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum,
                           @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="username", required=false)  String username,
                           @RequestParam(value="state", required=false) String state) {
        Page<User> page = userService.findPage(new Page<>(pageNum, pageSize), username, state);
        return Result.success(page);
    }

    @ApiOperation("新增用户信息")
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUsername())) {
            return Result.error (Constants.CODE_600,"参数错误！");
        }
        userService.saveOrUpdate(user);
        return Result.success();
    }

    @ApiOperation("删除单个用户信息")
    @ApiImplicitParam(paramType="path", name="id", value="用户ID", dataType="Integer")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    // 批量删除
    @ApiOperation("批量删除用户信息")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    @ApiOperation(value = "登录", notes = "登录后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        if (!StringUtils.hasText(userDTO.getUsername()) || !StringUtils.hasText(userDTO.getPassword())) {
            throw new ServiceException(Constants.CODE_400, "参数不能为空！");
        }
        return Result.success(userService.login(userDTO));
    }

    // 关联查询-根据角色ID查询角色名称，部门ID查询部门名称
    @ApiOperation(value = "个人信息")
    @GetMapping("/info")
    public Result findByUserId() {
        return Result.success(userService.getUserInfo());
    }

    @ApiOperation("修改密码")
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    @RequestMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.success();
    }

    @ApiOperation("用户菜单列表")
    @GetMapping("/menus")
    public Result getUserMenus() {
        return Result.success(menuService.getUserMenus());
    }

}

