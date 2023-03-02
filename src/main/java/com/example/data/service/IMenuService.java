package com.example.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.data.entity.Menu;
import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus();

    void removeMenu(Integer menuId);

    List<Menu> getUserMenus();

}
