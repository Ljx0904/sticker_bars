package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.students.Dto.PostsDto;
import com.ljx.students.common.R;
import com.ljx.students.entity.Classify;
import com.ljx.students.entity.Employee;
import com.ljx.students.entity.Module;
import com.ljx.students.entity.Posts;
import com.ljx.students.service.ClassifyService;
import com.ljx.students.service.EmployeeService;
import com.ljx.students.service.ModuleService;
import com.ljx.students.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ClassifyService classifyService;

    /*
    * 分页查询帖子(查询对应模块, 分类)
    * */
    @GetMapping("/list")
    public R<Page> list(int page,int pageSize,Long module,Long classifyId){

        log.info("查询所有帖子");

        //创建分页查询Page对象
        Page pageInfo=new Page(page,pageSize);


        //创建条件构造器的对象
        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Posts::getStatus,1);
        queryWrapper.eq(Posts::getModuleId,module);
        //按时间进行排序
        queryWrapper.orderByDesc(Posts::getCreateTime);
        if (classifyId!=0){
            queryWrapper.eq(Posts::getClassifyId,classifyId);
        }

        //进行分页查询
        postsService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /*
    * 分页查询帖子(后台管理, 返回模块, 分类名称)
    * */
    @GetMapping("/page")
    public R<Page> PostsPage(int page, int pageSize, String name, String fine , String beginTime,String endTime){

        //分页构造器
        Page<Posts> postsPage=new Page<>(page,pageSize);
        Page<PostsDto> postsDtoPage=new Page<>();

        //条件构造器
        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper();

        //模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(name),Posts::getName,name);
        queryWrapper.eq(StringUtils.isNotEmpty(fine),Posts::getFine,fine);
        queryWrapper.ge(StringUtils.isNotEmpty(beginTime),Posts::getCreateTime,beginTime)
                .le(StringUtils.isNotEmpty(endTime),Posts::getCreateTime,endTime);
        //排序
        queryWrapper.orderByDesc(Posts::getCreateTime);

        //分页查询
        postsService.page(postsPage,queryWrapper);

        BeanUtils.copyProperties(postsPage,postsDtoPage,"records");

        List<Posts> records = postsPage.getRecords();

        List<PostsDto> list= records.stream().map((item)->{

            PostsDto postsDto=new PostsDto();

            BeanUtils.copyProperties(item,postsDto);

            Long moduleId = item.getModuleId();
            Module module = moduleService.getById(moduleId);
            String moduleName = module.getName();

            Long classifyId = item.getClassifyId();
            if (classifyId!=null){
                Classify classify = classifyService.getById(classifyId);
                String classifyName = classify.getName();
                postsDto.setClassifyName(classifyName);
            }

            postsDto.setModuleName(moduleName);


            return postsDto;

        }).collect(Collectors.toList());

        postsDtoPage.setRecords(list);

        return R.success(postsDtoPage);
    }

    /*
    *
    * 批量修改帖子的状态(是否封禁)
    * */
    @PostMapping("/status/{status}")
    public R<String> updateState(@PathVariable int status,Long[]ids){

        log.info("修改的状态{},修改的id为{}",status,ids);

        Posts posts=new Posts();
        posts.setStatus(status);
        for (Long id : ids) {
            posts.setId(id);
            postsService.updateById(posts);
        }
        return R.success("修改成功");

    }
    /*
    * 修改帖子的状态(是否加精)
    * */
    @PostMapping("/fine/{fine}")
    public R<String> updateFine(@PathVariable int fine,Long ids){
        Posts posts=new Posts();
        posts.setFine(fine);
        posts.setId(ids);
        postsService.updateById(posts);
        return R.success("修改成功");

    }





    /*
    *
    * 按热度查询帖子
    * */
    @GetMapping("/HeatList")
    public R<List<Posts>> HeatList(){
        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Posts::getHeat);
        queryWrapper.last("limit 10");
        List<Posts> list = postsService.list(queryWrapper);
        return R.success(list);
    }
    /*
    *
    * 按是否加精查询
    * */

    @GetMapping("/FineList")
    public R<List<Posts>> fineList(){
        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Posts::getFine,1);
        queryWrapper.orderByDesc(Posts::getCreateTime);
        List<Posts> list = postsService.list(queryWrapper);
        return R.success(list);
    }


    /*
    *
    * 上传帖子
    * */
    @PostMapping("/add")
    public R<String> save(HttpServletRequest request, @RequestBody Posts posts){
        log.info("新增帖子{}",posts);

        Long empId = (Long)request.getSession().getAttribute("employee");
        log.info("贴主id{}",empId);
        posts.setEmployeeId(empId);

        Employee emp = employeeService.getById(empId);
        String empName = emp.getName();
        posts.setCreateUser(empName);
        postsService.save(posts);

        return R.success("上传成功");


    }
    /*
    * 根据帖子的id进行查询
    *
    * */

    @GetMapping("/{id}")
    public R<Posts> getById(@PathVariable String id){
        Posts p = postsService.getById(id);
        if (p!=null){
            Posts posts=new Posts();
            long l = p.getHeat() + 1;
            posts.setHeat(l);
            posts.setId(p.getId());
            /*p.setHeat(l);*/
            postsService.updateById(posts);
            return R.success(p);
        }
        return R.error("没有查到该帖子");
    }

    /*
    *修改帖子
    * */
    @PutMapping("/update")
    public R<String> update(HttpServletRequest request,@RequestBody Posts posts){

        //修改者id
        Long empId = (Long)request.getSession().getAttribute("employee");

        //帖子发布者id
        Long employeeId = posts.getEmployeeId();

        log.info("{}",empId .equals(employeeId) );
        if (!empId .equals(employeeId)){
            return R.error("只有作者才可以修改帖子");
        }
        postsService.updateById(posts);
        return R.success("修改成功");


    }
    /*
    * 删除帖子
    * */

    @DeleteMapping("/delete/{id}")
    public R<String> delete(HttpServletRequest request ,@PathVariable String id){
        Long employeeId = (Long) request.getSession().getAttribute("employee");

        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Posts::getEmployeeId,employeeId);
        queryWrapper.eq(Posts::getId,id);

        boolean remove = postsService.remove(queryWrapper);
        if (remove) {
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }


    }
    /*
    *
    * 根据用户id进行查询
    * */
    @GetMapping("/getByEmployeeId/{id}")
    public R<List<Posts>> getByEmployeeId(@PathVariable String id){

        LambdaQueryWrapper<Posts> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Posts::getEmployeeId,id);
        queryWrapper.orderByDesc(Posts::getCreateTime);
        queryWrapper.eq(Posts::getStatus,1);
        List<Posts> list = postsService.list(queryWrapper);

        return R.success(list);
    }

}
