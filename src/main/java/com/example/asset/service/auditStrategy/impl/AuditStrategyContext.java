package com.example.asset.service.auditStrategy.impl;

import com.example.asset.entity.Apply;
import com.example.asset.service.auditStrategy.AuditStrategyService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuditStrategyContext {

    private final Map<String, AuditStrategyService> auditStrategyMap = new ConcurrentHashMap<>();

    public AuditStrategyContext(Map<String, AuditStrategyService> strategyMap) {
        this.auditStrategyMap.putAll(strategyMap);
    }

    public AuditStrategyService getResource(Apply apply) {
        return auditStrategyMap.get(apply.getType());
    }

}
