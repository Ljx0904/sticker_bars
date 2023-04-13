package com.ljx.students.entity;

import lombok.Data;

@Data
public class Employee {
    private static final long serialVersionUID = 1L;
    //id
    private Long id;
    //名字
    private String name;
    //账号
    private String username;
    //密码
    private String password;
    //性别
    private String sex;
    //年龄
    private int age;
    //简介
    private String profile;
    //图片
    private String image;

}
