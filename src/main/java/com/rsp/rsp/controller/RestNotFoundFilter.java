package com.rsp.rsp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的error页面
 * @author sjb
 */
//@Controller
public class RestNotFoundFilter implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError(HttpServletRequest request) {
        //这里是自定义的返回内容
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401){
            return "/401";
        }else if(statusCode == 404){
            return ERROR_PATH;
        }
        return "";
    }

    @Override
    public String getErrorPath() {
        //其实没走这里
        return ERROR_PATH;
    }
}
