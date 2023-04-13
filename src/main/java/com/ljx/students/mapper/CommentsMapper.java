package com.ljx.students.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljx.students.entity.Comments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {
}
