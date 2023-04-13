package com.ljx.students.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;


/*
* 评论
* */
@Data
public class Comments {
    private Long id;
    //评论所在帖子id
    private Long postsId;
    //内容
    private String content;
    //创建人时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Long updateUser;

    //创建人id
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


}
