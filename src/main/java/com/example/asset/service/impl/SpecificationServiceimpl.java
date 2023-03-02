package com.example.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asset.entity.Specification;
import com.example.asset.mapper.SpecificationMapper;
import com.example.asset.service.ISpecificationService;
import org.springframework.stereotype.Service;

@Service
public class SpecificationServiceimpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {

}
