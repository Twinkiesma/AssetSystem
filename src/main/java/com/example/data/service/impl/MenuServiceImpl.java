package com.example.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.utils.RedisCache;
import com.example.data.entity.Menu;
import com.example.data.entity.User;
import com.example.data.mapper.MenuMapper;
import com.example.data.mapper.RoleMenuMapper;
import com.example.data.service.IMenuService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Menu> findMenus(){
        // 查询所有菜单数据
        List<Menu> menus = list();
        // 找出pid为null的一级菜单
        List<Menu> rootNodes = menus.stream().filter(rmenu -> rmenu.getPid() == null).collect(Collectors.toList());
        // 找出父级菜单的子菜单
        for(Menu pmenu : rootNodes){
            // stream()将数组转化为流数据, filter()设置过滤条件, collect(Collectors.toList())结束流转为List数组
            pmenu.setChildren(menus.stream().filter(cmenu -> pmenu.getId().equals(cmenu.getPid())).collect(Collectors.toList()));
        }
        return rootNodes;
    }

    @Transactional
    @Override
    public void removeMenu(Integer menuId) {
        // 删除菜单
        menuMapper.deleteById(menuId);
        // 删除菜单与角色的关联
        roleMenuMapper.deleteByMenuId(menuId);
    }

    @Override
    public List<Menu> getUserMenus() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        String redisKey = "menus_" + userId;
        // 从redis中获取菜单数据
        List<Menu> menus = redisCache.getCacheObject(redisKey);
        // 如果为空去数据库中获取
        if(Collections.isEmpty(menus)) {
            // 查出当前角色所有的菜单id集合
            List<Integer> roleMenus = roleMenuMapper.selectByRoleId(user.getRoleId());
            // 查出所有菜单-树结构
            List<Menu> allMenus = findMenus();
            // 筛选结果菜单
            List<Menu> finMenus = new ArrayList<>();
            // 筛选当前用户角色菜单
            for (Menu menu : allMenus) {
                if (roleMenus.contains(menu.getId())) {
                    finMenus.add(menu);
                }
                List<Menu> children = menu.getChildren();
                children.removeIf(child -> !roleMenus.contains(child.getId()));
            }
            // 把菜单数据存入Redis,userId作为key
            redisCache.setCacheObject("menus_" + userId, finMenus);
            return finMenus;
        }
        return menus;
    }

}
