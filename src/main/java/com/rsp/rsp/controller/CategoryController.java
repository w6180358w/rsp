package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseBody
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                   @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                   CategoryQuery categoryQuery,Integer draw){
        Page<Category> pageInfo = categoryService.findCategoryCriteria(start,size,categoryQuery);
        List<Category> orgList = pageInfo.getContent();
        Object[][] temp = new Object[orgList.size()][7];
        for (int i = 0;i<orgList.size();i++){
            temp[i][0] = orgList.get(i).getId();
            temp[i][1] = orgList.get(i).getId();
            temp[i][2] = orgList.get(i).getName();
            temp[i][3] = orgList.get(i).getType();
            temp[i][4] = orgList.get(i).getId();
        }

        return new R(orgList, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
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
