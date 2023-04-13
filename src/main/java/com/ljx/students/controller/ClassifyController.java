package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.students.Dto.ClassifyDto;
import com.ljx.students.common.R;
import com.ljx.students.entity.Classify;
import com.ljx.students.entity.Module;
import com.ljx.students.service.ClassifyService;
import com.ljx.students.service.ModuleService;
import com.ljx.students.service.impl.ClassifyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classify")
@Slf4j
public class ClassifyController {
    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private ModuleService moduleService;

    /*
    * 根据模块id查询分类
    * */
    @GetMapping("list/{moduleId}")
    public R<List<Classify>> getClassifyList(@PathVariable Long moduleId){
        log.info("{}",moduleId);
        LambdaQueryWrapper<Classify> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Classify::getModuleId,moduleId);
        queryWrapper.eq(Classify::getIsdelete,"0");
        queryWrapper.orderByAsc(Classify::getSort);
        List<Classify> list = classifyService.list(queryWrapper);
        return R.success(list);
    }


    /*
    * 分页查询
    * */
    @GetMapping("/page")
    public R<Page>  pageClassify(int page,int pageSize,String name){
        //创建分类对象
        Page<Classify> classifyPage=new Page<>(page,pageSize);
        Page<ClassifyDto> classifyDtoPage=new Page<>();

        //创建条件构造器
        LambdaQueryWrapper<Classify> queryWrapper=new LambdaQueryWrapper();
        //模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(name),Classify::getName,name);
        //排序
        queryWrapper.orderByAsc(Classify::getSort);
        queryWrapper.eq(Classify::getIsdelete,"0");
        //执行分页查询
        classifyService.page(classifyPage,queryWrapper);

        //对象拷贝,排除records
        BeanUtils.copyProperties(classifyPage,classifyDtoPage,"records");

        List<Classify> records = classifyPage.getRecords();

        List<ClassifyDto> list= records.stream().map((item)->{

            ClassifyDto classifyDto=new ClassifyDto();

            classifyDto.setClassify(item);
            /*log.info("classifyDto:{}",classifyDto);
            log.info("classifyDto:{}",item);*/

            Long moduleId = item.getModuleId();
            Module module = moduleService.getById(moduleId);
            if (module!=null){
                classifyDto.setModuleName(module.getName());
            }
            return classifyDto;


        }).collect(Collectors.toList());

        classifyDtoPage.setRecords(list);

        return R.success(classifyDtoPage);
    }
    /*
    * 删除分类
    * */
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id){
        if (id!=null){
            Classify classify=new Classify();
            classify.setId(id);
            classify.setIsdelete("1");
            boolean b = classifyService.updateById(classify);
            if (b){
                return R.success("删除成功");
            }
        }
        return R.error("删除失败");

    }

    /*
    * 添加分类
    * */
    @PostMapping("/add")
    public R<String> add(@RequestBody Classify classify){
        if (classify!=null){
            boolean save = classifyService.save(classify);
            if (save){
                return R.success("添加成功");
            }
        }
        return R.error("添加失败");
    }
    /*
    * 修改分类
    * */

    @PutMapping("/update")
    public R<String> update(@RequestBody Classify classify){
        if (classify!=null){
            boolean b = classifyService.updateById(classify);
            if (b){
                return R.success("修改成功");
            }
        }
        return R.error("修改失败");
    }
}
