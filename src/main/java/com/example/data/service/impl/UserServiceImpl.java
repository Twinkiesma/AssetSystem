package com.example.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.common.Constants;
import com.example.basic.common.exception.ServiceException;
import com.example.basic.utils.JwtUtil;
import com.example.basic.utils.RedisCache;
import com.example.data.dto.UserDTO;
import com.example.data.dto.UserPasswordDTO;
import com.example.data.entity.User;
import com.example.data.mapper.UserMapper;
import com.example.data.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Page<User> findPage(Page<User> page, String username, String state) {
        Page<User> page1 = userMapper.findPage(page, username, state);
        for(User user : page1.getRecords()) {
            user.setPassword(null);
        }
        return page1;
    }

    public boolean saveOrUpdate(User user) {
        if(user.getId() != null) {
            user.setPassword(null);
            userMapper.updateById(user);
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            User one = getOne(queryWrapper);
            if(one != null){
                throw new ServiceException(Constants.CODE_600, "用户名重复，请重新填写！");
            }
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bcryptPasswordEncoder.encode("123456"));
            userMapper.insert(user);
        }
        return true;
    }

    @Override
    public HashMap<String, String> login(UserDTO userDTO) {
        // 1.进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 2.如果认证没通过，给出对应提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("认证失败！");
        }
        // 3.如果认证通过了，使用userId生成token
        User user = (User) authenticate.getPrincipal();
        String userId = user.getId().toString();
        String jwt = JwtUtil.createToken(userId);
        // 4.把完整的用户信息存入Redis,userId作为key
        redisCache.setCacheObject("login_" + userId, user);
        // 5.返回信息
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("userId",userId);
        map.put("name",user.getName());
        return map;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据用户名去数据库查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User one = getOne(queryWrapper);
        // 2.如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(one)) {
            throw new RuntimeException("用户名或密码错误，请重试！");
        } else if(one.getState().equals("禁用")) {
            throw new RuntimeException("账号已禁用，请联系管理员！");
        }
        // 3.返回用户信息
        return one;
    }

    @Override
    public void logout() {
        Integer userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // 删除 redis 中对应用户信息和菜单信息
        redisCache.deleteObject("login_" + userId);
        redisCache.deleteObject("menus_" + userId);
    }

    @Override
    public User getUserInfo() {
        Integer userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userMapper.getUserInfo(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        Integer userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        String password = userMapper.getPassword(userId);  // 用户原密码
        // 用户输入密码 与 用户原密码 进行对比
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bcryptPasswordEncoder.matches(userPasswordDTO.getOldPassword(), password);
        if(matches) {
            String newPassword = bcryptPasswordEncoder.encode(userPasswordDTO.getNewPassword());
            userMapper.updatePassword(newPassword, userId);
        } else {
            throw new ServiceException(Constants.CODE_600, "密码错误，请重试！");
        }
    }

}
