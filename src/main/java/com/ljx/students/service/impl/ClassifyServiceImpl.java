package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Classify;
import com.ljx.students.mapper.ClassifyMapper;
import com.ljx.students.service.ClassifyService;
import org.springframework.stereotype.Service;

@Service

public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {
}
