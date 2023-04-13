package com.ljx.students.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljx.students.entity.Posts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper extends BaseMapper<Posts> {
}
