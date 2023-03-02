package com.example.asset.service.auditStrategy.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.asset.entity.Apply;
import com.example.asset.entity.Audit;
import com.example.asset.mapper.AssetMapper;
import com.example.asset.service.auditStrategy.AuditStrategyService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component("领用申请")
public class BorrowApply implements AuditStrategyService {

    @Resource
    private AssetMapper assetMapper;

    @Override
    public void auditApply(Apply apply, Audit audit) {
        JSONArray assets = apply.getAssets();
        for (Object asset : assets) { // 循环json数组
            JSONObject obj = (JSONObject) asset; // 得到一个json对象
            Integer quantity = (Integer) obj.get("quantity"); // 获取json对象中列名为quantity的值
            for (int i = 1; i <= quantity; i++) {
                if (audit.getResult().equals("同意")) {
                    assetMapper.borrow((Integer) obj.get("specId"), apply.getUserId(), (String) obj.get("position"), audit.getAuDate());
                } else if (audit.getResult().equals("不同意")) {
                    assetMapper.returnApply((Integer) obj.get("specId"),null);
                }
            }
        }
    }

}
