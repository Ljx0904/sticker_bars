package com.ljx.students.common;

/**
 * 基于ThreadLocal封装工具类, 用户保存或者获取当前登入id
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal=new ThreadLocal();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
