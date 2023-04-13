package com.ljx.students.Dto;

import com.ljx.students.entity.Comments;
import lombok.Data;

@Data
public class CommentsDto extends Comments {
    private String UserName;
    private String UserProfile;
}
