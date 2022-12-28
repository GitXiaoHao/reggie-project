package com.yh.reggie.controller.filter;

import com.alibaba.fastjson.JSON;
import com.yh.reggie.common.BaseContext;
import com.yh.reggie.common.Information;
import com.yh.reggie.common.Result;

import org.springframework.util.AntPathMatcher;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/25
 *
 * @author yu
 * 检查用户是否已经完成登陆
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    /**路径匹配器 支持通配符*/
    public static final AntPathMatcher MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求得URI
        String uri = request.getRequestURI();
        //定义不需要处理的uri
        String[] uris = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                //移动端发送短信
                "/user/sendMsg",
                // 移动端登陆
                "/user/login"
        };
        //判断本次请求是否需要处理
        if (!check(uris, uri)) {
            //需要处理
            log.info("拦截到请求:{}", uri);

            //获取登录对象
            Object employee = request.getSession().getAttribute(Information.USER_INFO);
            if(employee != null){
                if (BaseContext.getCurrent() == null) {
                    BaseContext.setCurrent(employee);
                }
                filterChain.doFilter(request, response);
                return;
            }
            //4-2判断移动端登录状态，如果已登录，则直接放行
            if (request.getSession().getAttribute(Information.USER) != null) {
                //把用户id存储到本地的threadLocal
                Long userId = (Long) request.getSession().getAttribute(Information.USER);
                BaseContext.setCurrent(userId);
                filterChain.doFilter(request, response);
            }else{
                //如果登陆对象等于空
                response.getWriter().write(JSON.toJSONString(Result.error(Information.NOT_LOGIN)));
            }
            return;
        }
        //放行
        filterChain.doFilter(request, response);
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param uris 不匹配的uris
     * @param requestUri 访问的uri
     * @return 如果匹配就返回true 匹配不上就false
     */
    public boolean check(String[] uris, String requestUri) {
        for (String uri : uris) {
            boolean match = MATCHER.match(uri, requestUri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
