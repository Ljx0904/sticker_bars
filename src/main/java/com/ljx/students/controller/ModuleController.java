package com.ljx.students.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.students.common.R;
import com.ljx.students.entity.Module;
import com.ljx.students.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    /*
    *查询模块数据
    * */
    @GetMapping("/list")
    public R<List<Module>> list(){

        LambdaQueryWrapper<Module> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Module::getStatus,1);
        queryWrapper.orderByAsc(Module::getSort);
        List<Module> list = moduleService.list(queryWrapper);
        return R.success(list);
    }

    /*
    * 分页查询模块数据
    * */

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Module> modulePage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Module> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByAsc(Module::getSort);
        queryWrapper.eq(Module::getStatus,1);
        moduleService.page(modulePage,queryWrapper);
        return R.success(modulePage);
    }

    /*
    * 新增模块
    * */

    @PostMapping("/add")
    public R<String> add(@RequestBody Module module){
        if (module!=null){
            boolean b = moduleService.save(module);
            if (b){
                return R.success("新增模块成功");
            }
        }
        return R.error("新增模块失败");
    }

    /*
    *
    * 修改模块
    * */
    @PutMapping("/update")
    public R<String> update(@RequestBody Module module){
        if (module!=null){
            boolean b = moduleService.updateById(module);
            if (b){
                return R.success("修改模块成功");
            }
        }
        return R.error("修改模块失败");
    }

    /*
    *
    * 删除模块
    * */
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id){
        if (id!=null){
            Module module=new Module();
            module.setId(id);
            module.setStatus("0");
            boolean b = moduleService.updateById(module);
            if (b){
                return R.success("模块删除成功");
            }
        }
        return R.error("删除模块失败");
    }
}
