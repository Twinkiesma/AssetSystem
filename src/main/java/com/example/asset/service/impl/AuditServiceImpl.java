package com.example.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asset.entity.Apply;
import com.example.asset.entity.Audit;
import com.example.asset.mapper.ApplyMapper;
import com.example.asset.mapper.AuditMapper;
import com.example.asset.service.IAuditService;
import com.example.asset.service.auditStrategy.impl.AuditStrategyContext;
import com.example.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class AuditServiceImpl extends ServiceImpl<AuditMapper, Audit> implements IAuditService {

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private AuditMapper auditMapper;

    @Autowired
    private AuditStrategyContext auditStrategyContext;

    @Transactional
    @Override
    public boolean save(Audit audit) {
        audit.setAuUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        auditMapper.insert(audit);
        UpdateWrapper<Apply> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",audit.getApId()).set("state", "已办结");
        applyMapper.update(null, updateWrapper);
        Apply apply = applyMapper.selectById(audit.getApId());
        auditStrategyContext.getResource(apply).auditApply(apply, audit);
        return true;
    }

}
