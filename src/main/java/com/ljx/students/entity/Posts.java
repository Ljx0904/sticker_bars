package com.ljx.students.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Posts {
    private static final long serialVersionUID = 1L;
    //id
    private Long id;
    //帖子标题
    private String name;
    //帖子内容
    private String content;
    //发布人id
    private Long employeeId;
    //0 停售 1 起售
    private Integer status;

    //添加时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /*//修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)*/
    private LocalDateTime updateTime;

    //图片
    private String image;

    //发帖人名字
    private String createUser;

    //模块Id
    private Long moduleId;

    //分类id
    private Long classifyId;

    //帖子热度
    private Long heat;

    //0:无精品 1:精品
    private Integer fine;

}
