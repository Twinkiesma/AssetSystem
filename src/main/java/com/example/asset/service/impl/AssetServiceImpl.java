package com.example.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asset.dto.WareDTO;
import com.example.asset.entity.Asset;
import com.example.asset.mapper.AssetMapper;
import com.example.asset.service.IAssetService;
import com.example.asset.vo.AssetVo;
import com.example.asset.vo.BorrowVo;
import com.example.data.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements IAssetService {

    @Resource
    private AssetMapper assetMapper;

    @Override
    public Page<AssetVo> findPage(Page<AssetVo> page,String sortname, String brand, String state, String name,
                                  String position, String date, String deptname, Integer userId, String assetname) {
        return assetMapper.findPage(page, sortname, brand, state, name, position, date, deptname, userId, assetname);
    }

    @Transactional
    @Override
    public void saveWare(WareDTO wareDTO){
        int amount = wareDTO.getAmount();
        for(int i = 1 ; i <= amount; i ++) {
            assetMapper.saveWare(wareDTO);
        }
        assetMapper.saveNumber();
    }

    @Override
    public Page<BorrowVo> findBorrowPage(Page<BorrowVo> page, String assetname, String sortname, String brand) {
        return assetMapper.findBorrowPage(page, assetname, sortname, brand);
    }

    @Override
    public void restore(Integer id) {
        UpdateWrapper<Asset> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                     .set("state", "在用")
                     .set("reason", null);
        assetMapper.update(null, updateWrapper);
    }

    @Override
    public void discard(Integer id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate date = LocalDate.now();
        Integer userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UpdateWrapper<Asset> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                     .set("state", "已报废")
                     .set("user_id", userId)
                     .set("position", null)
                     .set("date", date.format(formatter));
        assetMapper.update(null, updateWrapper);
    }

}
