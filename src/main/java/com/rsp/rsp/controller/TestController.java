package com.rsp.rsp.controller;

import com.rsp.rsp.dao.CategoryRepository;
import com.rsp.rsp.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * test database
 * @author sjb
 */
@RestController
@RequestMapping("/employee")
public class TestController {
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Category> queryAll(){
        return categoryRepository.findAll();
    }
    @Autowired
    private CategoryRepository categoryRepository;
}
