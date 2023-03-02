package com.example.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.asset.entity.Apply;
import com.example.asset.vo.AuditVo;
import org.apache.ibatis.annotations.Param;

public interface ApplyMapper extends BaseMapper<Apply> {

    Page<AuditVo> findPage(Page<AuditVo> page, @Param("date") String date, @Param("type") String type, @Param("name") String name, @Param("userId") Integer userId,
                           @Param("deptname") String deptname, @Param("state") String state, @Param("auDate") String auDate, @Param("auName") String auName, @Param("result") String result);

}
