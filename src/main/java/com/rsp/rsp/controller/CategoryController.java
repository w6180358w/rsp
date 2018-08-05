package com.rsp.rsp.controller;

import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 大类
 * @author sjb
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/queryAll")
    public Page<Category> queryAll(@RequestParam(value ="page",defaultValue ="0")Integer page,
                                   @RequestParam(value ="size",defaultValue ="10")Integer size,
                                   CategoryQuery categoryQuery){
        return categoryService.findCategoryCriteria(page,size,categoryQuery);
    }

    @PostMapping("/save")
    public String save(Category category){
        try {
            categoryService.save(category);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Category category){
        try {
            categoryService.update(category);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        try {
            categoryService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
