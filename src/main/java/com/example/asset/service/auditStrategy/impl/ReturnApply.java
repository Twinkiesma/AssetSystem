package com.example.asset.service.auditStrategy.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.asset.entity.Apply;
import com.example.asset.entity.Asset;
import com.example.asset.entity.Audit;
import com.example.asset.mapper.AssetMapper;
import com.example.asset.service.auditStrategy.AuditStrategyService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component("归还申请")
public class ReturnApply implements AuditStrategyService {

    @Resource
    private AssetMapper assetMapper;

    @Override
    public void auditApply(Apply apply, Audit audit) {
        JSONArray assets = apply.getAssets();
        for (Object asset : assets) { // 循环json数组
            JSONObject obj = (JSONObject) asset; // 得到一个json对象
            Integer id = (Integer) obj.get("id"); // 获得资产ID
            if (audit.getResult().equals("同意")) {
                assetMapper.returnApply(null, id);
            } else if (audit.getResult().equals("不同意")) {
                UpdateWrapper<Asset> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",id).set("state", "在用");
                assetMapper.update(null, updateWrapper);
            }
        }
    }

}
