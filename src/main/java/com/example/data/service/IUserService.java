package com.example.data.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.data.dto.UserDTO;
import com.example.data.dto.UserPasswordDTO;
import com.example.data.entity.User;
import java.util.HashMap;

public interface IUserService extends IService<User> {

    HashMap<String, String> login(UserDTO userDTO);

    Page<User> findPage(Page<User> page, String username, String state);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    User getUserInfo();

    void logout();

}
