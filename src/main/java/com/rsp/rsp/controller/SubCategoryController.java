package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.service.CategoryService;
import com.rsp.rsp.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 小类
 * @author sjb
 */
@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @RequestMapping()
    public ModelAndView subCategory(){
        return new ModelAndView("subCategory.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                      @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                      SubCategoryQuery subCategoryQuery,Integer draw){
        Page<SubCategory> pageInfo = subCategoryService.findSubCategoryCriteria(start,size,subCategoryQuery);
        return new R(pageInfo.getContent(), (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView addSubCategory(Long id, Model model){
        SubCategory subCategory;
        if(null!=id){
            subCategory = subCategoryService.findById(id);
        }else{
            subCategory = new SubCategory();
            subCategory.setId(0L);
            subCategory.setCategoryId(0L);
        }
        model.addAttribute("subCategory",subCategory);
        return new ModelAndView("addSubCategory.html");
    }

    @PostMapping("/save")
    public String save(SubCategory subCategory){
        try {
            //添加失败传提示信息
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

    @PostMapping("/delete")
    public String delete(@RequestParam(value ="id" )Long id){
        try {
            subCategoryService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
