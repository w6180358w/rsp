package com.rsp.rsp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义的error页面
 * @author sjb
 */
@Controller
public class RestNotFoundFilter implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError() {
        //这里是自定义的返回内容
        return ERROR_PATH;
    }

    @Override
    public String getErrorPath() {
        //其实没走这里
        return ERROR_PATH;
    }
}
