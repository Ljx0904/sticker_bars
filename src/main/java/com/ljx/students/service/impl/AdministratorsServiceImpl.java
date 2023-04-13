package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Administrators;
import com.ljx.students.mapper.AdministratorsMapper;
import com.ljx.students.service.AdministratorsService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorsServiceImpl extends ServiceImpl<AdministratorsMapper, Administrators>implements AdministratorsService {
}
