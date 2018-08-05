package com.rsp.rsp.controller;

import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 小类
 * @author sjb
 */
@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @RequestMapping("/queryAll")
    public Page<SubCategory> queryAll(@RequestParam(value ="page",defaultValue ="0")Integer page,
                                   @RequestParam(value ="size",defaultValue ="10")Integer size,
                                   SubCategoryQuery subCategoryQuery){
        return subCategoryService.findSubCategoryCriteria(page,size,subCategoryQuery);
    }

    @PostMapping("/save")
    public String save(SubCategory subCategory){
        try {
            subCategoryService.save(subCategory);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(SubCategory subCategory){
        try {
            subCategoryService.update(subCategory);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        try {
            subCategoryService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
