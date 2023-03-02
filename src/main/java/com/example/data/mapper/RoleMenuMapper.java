package com.example.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.data.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Delete("delete from asset.role_menu where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select menu_id from asset.role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);

    @Delete("delete from asset.role_menu where menu_id = #{menuId}")
    void deleteByMenuId(@Param("menuId") Integer menuId);

}
