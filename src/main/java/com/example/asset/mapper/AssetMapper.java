package com.example.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.asset.dto.WareDTO;
import com.example.asset.entity.Asset;
import com.example.asset.vo.AssetVo;
import com.example.asset.vo.BorrowVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AssetMapper extends BaseMapper<Asset> {

    Page<AssetVo> findPage(Page<AssetVo> page, @Param("sortname") String sortname, @Param("brand") String brand, @Param("state") String state,
                           @Param("name") String name, @Param("position") String position, @Param("date") String date, @Param("deptname") String deptname,
                           @Param("userId") Integer userId, @Param("assetname") String assetname);

    Page<BorrowVo> findBorrowPage(Page<BorrowVo> page, @Param("assetname") String assetname, @Param("sortname") String sortname, @Param("brand") String brand);

    @Insert("insert into asset.asset(assetname, spec_id, price, state) values (#{assetname}, #{specId}, #{price}, '闲置')")
    void saveWare(WareDTO wareDTO);

    @Update("update asset.asset set number = concat('PZ',lpad(spec_id,3,0),lpad(id,4,0)) where number is null")
    void saveNumber();

    @Update("update asset.asset " +
            "set state = concat(#{type}, '中') " +
            "where spec_id = #{specId} and state = '闲置' " +
            "or id = #{id} " +
            "limit 1")
    void foreApply(Integer specId, Integer id, String type);

    @Update("update asset.asset " +
            "set state = '闲置', user_id = null, position = null, date = null " +
            "where spec_id = #{specId} and state = '领用申请中' " +
            "or id = #{id} " +
            "limit 1;")
    void returnApply(Integer specId, Integer id);

    @Update("update asset.asset " +
            "set user_id = #{userId}, position = #{position}, date = #{auDate}, state = '在用'" +
            "where spec_id = #{specId} and state = '领用申请中' " +
            "limit 1;")
    void borrow(Integer specId, Integer userId, String position, String auDate);

}
