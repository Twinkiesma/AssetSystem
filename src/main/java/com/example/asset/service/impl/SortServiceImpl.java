package com.example.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asset.entity.Sort;
import com.example.asset.mapper.SortMapper;
import com.example.asset.service.ISortService;
import com.example.asset.vo.SortVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements ISortService {

    @Resource
    private SortMapper sortMapper;

    @Override
    public List<SortVo> findSort(){
        // 联表查询所有数据
        List<SortVo> allNodes = sortMapper.SVlist();

        // 将规格子节点的品类名和状态设空，规格ID设负
        List<SortVo> cNodes = allNodes.stream().filter(csort -> csort.getId().equals(csort.getSortId())).collect(Collectors.toList());
        cNodes.forEach(object -> object.setSortname(null));
        cNodes.forEach(object -> object.setState(null));
        cNodes.forEach(object -> object.setSpecId(-object.getSpecId()));

        // 找出SortId为0的父节点
        List<SortVo> rootNodes = allNodes.stream().filter(rsort -> rsort.getSortId() == 0).collect(Collectors.toList());

        // 找出规格子节点加入父节点
        for(SortVo psort : rootNodes){
            psort.setChildren(cNodes.stream().filter(csort -> psort.getId().equals(csort.getSortId())).collect(Collectors.toList()));
        }

        return rootNodes;
    }

}
