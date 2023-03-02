package com.example.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.data.entity.RoleMenu;
import com.example.data.mapper.RoleMenuMapper;
import com.example.data.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    private  RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void savePurview(Integer roleId, List<Integer> menuIds){
        // 先删除所有角色-菜单关联，再添加
        roleMenuMapper.deleteByRoleId(roleId);
        for(Integer menuId : menuIds){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getPurview(Integer roleId){
        return roleMenuMapper.selectByRoleId(roleId);
    }

}
