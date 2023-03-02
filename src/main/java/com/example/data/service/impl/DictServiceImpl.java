package com.example.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.data.entity.Dict;
import com.example.data.mapper.DictMapper;
import com.example.data.service.IDictService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
