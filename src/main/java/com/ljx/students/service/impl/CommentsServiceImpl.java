package com.ljx.students.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.students.entity.Comments;
import com.ljx.students.mapper.CommentsMapper;
import com.ljx.students.service.CommentsService;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
}
