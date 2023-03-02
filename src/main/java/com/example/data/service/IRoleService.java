package com.example.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.data.entity.Role;

public interface IRoleService extends IService<Role> {

    void removeRole(Integer roleId);

}
