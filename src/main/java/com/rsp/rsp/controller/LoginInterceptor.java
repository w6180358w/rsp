package com.rsp.rsp.controller;

import com.rsp.rsp.config.PropertiesConif;
import com.rsp.rsp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截
 * @author sjb
 */
@Controller
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PropertiesConif conif;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();

        //是否进行登陆拦截
        if(!doLoginInterceptor(path, basePath) ){
            return true;
        }


        //如果登录了，会把用户信息存进session
        HttpSession session = request.getSession();
        User userLogin = (User) session.getAttribute("user");
        if(userLogin==null){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = new User(username,password);
            if(conif.getUsername().equals(username) && conif.getPassword().equals(password)){
                session.setAttribute("user",user);
                return true;
            }else {
                response.sendRedirect(request.getContextPath()+"index");
                return false;
            }
        }
        return true;
    }

    /**
     * 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        notLoginPaths.add("/index");
        notLoginPaths.add("/css");
        notLoginPaths.add("/js");
        notLoginPaths.add("/images");
        notLoginPaths.add("/plugins");
        notLoginPaths.add("/phone");
        notLoginPaths.add("/fallin-information");
        notLoginPaths.add("/list");
        notLoginPaths.add("/details");
        if(notLoginPaths.contains(path)) {
            return false;
        }
        return true;
    }

}
