package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.students.common.R;
import com.ljx.students.entity.Administrators;
import com.ljx.students.entity.Employee;
import com.ljx.students.service.AdministratorsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/administrators")
@Slf4j
public class AdministratorsController {
    @Autowired
    private AdministratorsService administratorsService;


    /*
    * 员工登入
    * */
    @PostMapping("/login")
    public R<Administrators> login (HttpServletRequest request, @RequestBody Administrators administrators){
        //1.进行md5加密
        String password = administrators.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据用户名查询数据库
        LambdaQueryWrapper<Administrators> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Administrators::getUsername,administrators.getUsername());
        Administrators adm = administratorsService.getOne(queryWrapper);
        //3.如果没有查到, 返回登入失败的结果
        if (adm==null){
            return R.error("您输入的账号或密码错误,请重新输入");
        }
        //4.进行密码校验, 密码错误, 返回的登入失败的结果
        if (!adm.getPassword().equals(password)){
            return R.error("你输入的账号或密码错误, 请重新输入");

        }
        //5.登入成功,将id储存起来返回登入成功
        request.getSession().setAttribute("administrators",adm.getId());

        return R.success(adm);
    }

    /*
    * 员工退出
    * */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.removeAttribute("administrators");
        return R.success("删除成功");
    }

    /*
    *分页查询员工
    * */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        Page pageInfo =new Page(page,pageSize);

        LambdaQueryWrapper<Administrators> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Administrators::getName,name);
        queryWrapper.orderByDesc(Administrators::getUpdateTime);

        administratorsService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /*
    *
    * 添加员工
    * */
    @PostMapping("/add")
    public R<String> add(@RequestBody Administrators administrators){
        log.info("添加员工{}",administrators);
        if (administrators!=null){
            //设置默认密码是123456, 进行md5加密
            administrators.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            //添加数据
            boolean save = administratorsService.save(administrators);
            //判断是否成功
            if (save){
                return R.success("添加成功");
            }
        }
        return R.error("添加失败");

    }
    /*
    *
    * 根据id查询员工信息(修改时回显)
    * */

    @GetMapping("{id}")
    public R<Administrators> getById(@PathVariable Long id){
        Administrators administrators = administratorsService.getById(id);
        return R.success(administrators);
    }

    /*
    * 修改员工信息
    * */

    @PutMapping("/update")
    public R<String> update(@RequestBody Administrators administrators){
        if (administrators!=null){
            boolean b = administratorsService.updateById(administrators);
            if (b){
                return R.success("修改成功");
            }
        }
        return R.error("修改失败");
    }









}
