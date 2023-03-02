package com.example.asset.service.auditStrategy;

import com.example.asset.entity.Apply;
import com.example.asset.entity.Audit;

public interface AuditStrategyService {

    void auditApply(Apply apply, Audit audit);

}
