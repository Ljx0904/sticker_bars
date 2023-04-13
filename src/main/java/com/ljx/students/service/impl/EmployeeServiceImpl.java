package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Employee;
import com.ljx.students.mapper.EmployeeMapper;
import com.ljx.students.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
