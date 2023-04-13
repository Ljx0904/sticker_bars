package com.ljx.students.filer;


import com.alibaba.fastjson.JSON;
import com.ljx.students.common.BaseContext;
import com.ljx.students.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //工具类:路径匹配器, 支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获得本次请求

        String uri = request.getRequestURI();

        log.info("拦截到请求{}",uri);


        //2.判断本次请求是否需要处理

        //定义不需要拦截的请求途径数组
        String[]urls=new String[]{
                "/employee/login",
                "/administrators/login",
                "/employee/logout",
                "/administrators/logout",
                "/employee/enroll",
                "/front/**",
                "/backend/**",
                "common/**"

        };
        boolean check = check(urls, uri);
        //3.如果不需要处理直接放行
        if (check){
            log.info("本次请求{}不需要处理",uri);
            filterChain.doFilter(request,response);
            return;
        }



        //4.1判断是否登入
        if (request.getSession().getAttribute("employee")!=null){
            //.如果登入放行
            log.info("请求{}已登入",uri);
            filterChain.doFilter(request,response);
            Long employeeId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeId);
            return;
        }

        //4.2判断是否登入
        if (request.getSession().getAttribute("administrators")!=null){
            //.如果登入放行
            log.info("请求{}已登入",uri);
            //将操作人的id储存在ThreadLocal中
            Long administratorsId = (Long) request.getSession().getAttribute("administrators");
            BaseContext.setCurrentId(administratorsId);
            filterChain.doFilter(request,response);
            return;
        }

        //5. 没有登入进行拦截
        log.info("{}未登入",uri);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;




    }
    public boolean check(String[]urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;


    }

}
