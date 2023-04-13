package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.students.Dto.CommentsDto;
import com.ljx.students.common.R;
import com.ljx.students.entity.Comments;
import com.ljx.students.entity.Employee;
import com.ljx.students.service.CommentsService;
import com.ljx.students.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private EmployeeService employeeService;

    /*
    * 查询评论
    * */
    @GetMapping("/list")
    public R<Page> CommentsList(int page,int pageSize,Long postsId ){
        Page<Comments> commentsPage=new Page<>(page,pageSize);
        Page<CommentsDto> commentsDtoPage=new Page<>();

        LambdaQueryWrapper<Comments> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Comments::getPostsId,postsId);
        queryWrapper.orderByDesc(Comments::getCreateTime);
        commentsService.page(commentsPage,queryWrapper);

        BeanUtils.copyProperties(commentsPage,commentsDtoPage,"records");

        List<Comments> records = commentsPage.getRecords();

        List<CommentsDto> list= records.stream().map((item)->{
            CommentsDto commentsDto=new CommentsDto();

            BeanUtils.copyProperties(item,commentsDto);

            Long createUser = item.getCreateUser();

            Employee employee = employeeService.getById(createUser);

            if (employee!=null){
                commentsDto.setUserName(employee.getName());
                commentsDto.setUserProfile(employee.getImage());
            }
            return commentsDto;
        }).collect(Collectors.toList());
        commentsDtoPage.setRecords(list);

        return R.success(commentsDtoPage);
    }
    /*
    * 添加评论
    * */
    @PostMapping("/add")
    public R<String> add(@RequestBody Comments comments){
        if (comments!=null){
            boolean save = commentsService.save(comments);
            if (save){
                return R.success("添加成功");
            }
        }
        return R.error("添加失败");

    }
}
