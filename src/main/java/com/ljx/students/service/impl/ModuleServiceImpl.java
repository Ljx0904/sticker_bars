package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Module;
import com.ljx.students.mapper.ModuleMapper;
import com.ljx.students.service.ModuleService;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {
}
