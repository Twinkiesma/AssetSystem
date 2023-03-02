package com.example.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.asset.entity.Sort;
import com.example.asset.vo.SortVo;
import java.util.List;

public interface ISortService extends IService<Sort> {

    List<SortVo> findSort();

}
