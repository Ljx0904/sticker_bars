package com.ljx.students.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;


/*
*   全局异常处理
* */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        log.error(message);
        if (message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            return R.error("用户名"+split[2]+"已存在");

        }else {
            return R.error("未知错误");
        }


    }
    @ExceptionHandler(FileNotFoundException.class)
    public void exceptionHandlers(FileNotFoundException ex){
        String message = ex.getMessage();
        log.error(message);
        return;
    }


}
