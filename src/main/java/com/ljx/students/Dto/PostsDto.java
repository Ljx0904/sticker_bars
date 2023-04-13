package com.ljx.students.Dto;

import com.ljx.students.entity.Posts;
import lombok.Data;

@Data
public class PostsDto extends Posts {
    private String ModuleName;
    private String ClassifyName;
}
