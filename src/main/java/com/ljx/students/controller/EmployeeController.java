package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ljx.students.common.R;
import com.ljx.students.entity.Employee;
import com.ljx.students.entity.Posts;
import com.ljx.students.service.EmployeeService;
import com.ljx.students.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PostsService postsService;
    /*
    * 用户登入
    * */
    @PostMapping("/login")
    public R<Employee> login (HttpServletRequest request, @RequestBody Employee employee){
        //1.进行md5加密
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3.如果没有查到, 返回登入失败的结果
        if (emp==null){
//            return false;
            return R.error("您输入的账号或密码错误,请重新输入");
        }
        //4.进行密码校验, 密码错误, 返回的登入失败的结果
        if (!emp.getPassword().equals(password)){
            return R.error("你输入的账号或密码错误, 请重新输入");
//            return false;
        }
        //5.登入成功,将id储存起来返回登入成功
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
//        return true;
    }

    /*
    * 用户退出
    * */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");

    }

    /*
    *
    * 用户注册
    * */
    @PostMapping("/enroll")
    public R<String> enroll(HttpServletRequest request,@RequestBody Employee employee){

        //获得密码, 并进行md5加密
        String password = employee.getPassword();
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
//        long userId = (long) request.getSession().getAttribute("employee");
//        employee.setId(userId);
        log.info(employee.toString());
        //获得随机名称, 并进行添加
        String name = RandomStringUtils.randomAlphanumeric(7);
        employee.setName(name);
        employee.setImage("ced4c7c1-130d-4e84-8efd-0a532493a024.png");
        //进行添加
        employeeService.save(employee);

        return R.success("注册成功");
    }
    //用户修改

    @PutMapping("/update")
    public R<Employee> update(@RequestBody Employee employee){
        boolean b = employeeService.updateById(employee);
        if (b){
            /*LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
            Employee emp = employeeService.getById(employee.getId());*/
            /*LambdaQueryWrapper<Posts> queryWrapper1=new LambdaQueryWrapper<>();*/
//            queryWrapper1.eq(Posts::getEmployeeId,employee.getId());
            /*Posts p=new Posts();*/
            /*p.setCreateUser(employee.getName());*/
            LambdaUpdateWrapper<Posts> updateWrapper=new LambdaUpdateWrapper<>();
                    updateWrapper.set(Posts::getCreateUser,employee.getName())
                    .eq(Posts::getEmployeeId,employee.getId());
            boolean boo = postsService.update(null,updateWrapper);
            /*postsService.update(queryWrapper1,);*/
            return R.success(employee);

        }
        return R.error("修改失败");
    }
    /*
    * 根据用户id查询用户
    * */

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询用户信息,id为{}",id);
        if (null!=id) {
            Employee emp = employeeService.getById(id);
            return R.success(emp);
        }
        return R.error("查询失败");
    }


}
