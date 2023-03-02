package com.example.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.asset.entity.Apply;
import com.example.asset.vo.AuditVo;

public interface IApplyService extends IService<Apply> {

    Page<AuditVo> findPage(Page<AuditVo> page, String date, String type, String name, Integer userId,
                           String deptname, String state, String auDate, String auName, String result);

}
