package com.example.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.data.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page, @Param("username") String username, @Param("state") String state);

    @Update("update asset.user set password = #{newPassword} where id = #{userId}")
    int updatePassword(String newPassword, Integer userId);

    User getUserInfo(Integer userId);

    @Select("select user.password from asset.user where id = #{userId}")
    String getPassword(Integer userId);
}
