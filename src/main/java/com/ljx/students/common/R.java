package com.ljx.students.common;

import lombok.Data;

/*
*通用返回结果
* */
@Data
public class R<T> {
    private Integer code;//编码: 1为成功, 2或其他数字为失败
    private String msg;//错误信息
    private T data;//数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

}
