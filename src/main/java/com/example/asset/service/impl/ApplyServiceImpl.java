package com.example.asset.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asset.entity.Apply;
import com.example.asset.mapper.ApplyMapper;
import com.example.asset.mapper.AssetMapper;
import com.example.asset.service.IApplyService;
import com.example.asset.vo.AuditVo;
import com.example.data.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Resource
    private AssetMapper assetMapper;

    @Resource
    private ApplyMapper applyMapper;

    @Transactional
    @Override
    public boolean save(Apply apply) {
        apply.setUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        applyMapper.insert(apply);
        JSONArray assets =  apply.getAssets();
        for (Object asset : assets) { // 循环json数组
            JSONObject obj = (JSONObject) asset; // 得到一个json对象
            Integer quantity = (Integer) obj.get("quantity"); // 获取json对象中列名为quantity的值
            if (quantity != null) {
                for (int i = 1; i <= quantity; i++) { // 预申请-领用
                    assetMapper.foreApply((Integer) obj.get("specId"),null, apply.getType());
                }
            }
            assetMapper.foreApply(null, (Integer) obj.get("id"), apply.getType()); // 预申请-归还/维修
        }
        return true;
    }

    @Override
    public Page<AuditVo> findPage(Page<AuditVo> page, String date, String type, String name, Integer userId,
                                  String deptname, String state, String auDate, String auName, String result) {
        return applyMapper.findPage(page, date, type, name, userId, deptname, state, auDate, auName, result);
    }

}
