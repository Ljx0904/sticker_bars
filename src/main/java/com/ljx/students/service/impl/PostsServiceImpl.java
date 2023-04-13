package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Posts;
import com.ljx.students.mapper.PostsMapper;
import com.ljx.students.service.EmployeeService;
import com.ljx.students.service.PostsService;
import org.springframework.stereotype.Service;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {
}
