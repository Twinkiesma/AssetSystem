package com.example.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.asset.entity.Sort;
import com.example.asset.vo.SortVo;
import java.util.List;

public interface SortMapper extends BaseMapper<Sort> {

    List<SortVo> SVlist();

}
