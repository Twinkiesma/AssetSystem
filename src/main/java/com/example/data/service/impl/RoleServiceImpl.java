package com.example.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.data.entity.Role;
import com.example.data.mapper.RoleMapper;
import com.example.data.mapper.RoleMenuMapper;
import com.example.data.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void removeRole(Integer roleId){
        roleMapper.deleteById(roleId);
        roleMenuMapper.deleteByRoleId(roleId);
    }

}
