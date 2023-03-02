package com.example.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.asset.dto.WareDTO;
import com.example.asset.entity.Asset;
import com.example.asset.vo.AssetVo;
import com.example.asset.vo.BorrowVo;

public interface IAssetService extends IService<Asset> {

    Page<AssetVo> findPage(Page<AssetVo> page, String sortname, String brand, String state, String name,
                           String position, String date, String deptname, Integer userId, String assetname);

    Page<BorrowVo> findBorrowPage(Page<BorrowVo> page, String assetname, String sortname, String brand);

    void saveWare(WareDTO wareDTO);

    void restore(Integer id);

    void discard(Integer id);

}
