package com.example.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.data.entity.RoleMenu;
import java.util.List;

public interface IRoleMenuService extends IService<RoleMenu> {

    void savePurview(Integer roleId, List<Integer> menuIds);

    List<Integer> getPurview(Integer roleId);

}
