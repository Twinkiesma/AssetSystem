package com.example.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@TableName("user")
@ApiModel(value = "User对象", description = "用户信息表")
public class User implements UserDetails {

      @ApiModelProperty("账号ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("账号状态")
      private String state;

      @ApiModelProperty("角色ID")
      private Integer roleId;

      @TableField(exist = false)
      @ApiModelProperty("角色名称")
      private String role;

      @ApiModelProperty("姓名")
      private String name;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("部门ID")
      private Integer deptId;

      @TableField(exist = false)
      @ApiModelProperty("部门名称")
      private String department;

      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
      }

      @Override
      public String getPassword() {
            return password;
      }

      @Override
      public String getUsername() {
            return username;
      }

      @Override
      public boolean isAccountNonExpired() {
            return true;
      }

      @Override
      public boolean isAccountNonLocked() {
            return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
            return true;
      }

      @Override
      public boolean isEnabled() {
            return true;
      }

}
