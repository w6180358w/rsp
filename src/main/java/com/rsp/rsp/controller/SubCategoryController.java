package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R queryAll(@RequestParam(value ="iDisplayStart",defaultValue ="0")Integer start,
                                      @RequestParam(value ="iDisplayLength",defaultValue ="10")Integer size,
                                      SubCategoryQuery subCategoryQuery,Integer draw){
        Page<SubCategory> pageInfo = subCategoryService.findSubCategoryCriteria(start,size,subCategoryQuery);
        List<SubCategory> orgList = pageInfo.getContent();
        Object[][] temp = new Object[orgList.size()][6];
        for (int i = 0;i<orgList.size();i++){
            temp[i][0] = orgList.get(i).getId();
            temp[i][1] = orgList.get(i).getId();
            temp[i][2] = orgList.get(i).getName();
            temp[i][3] = orgList.get(i).getCategoryId();
            temp[i][4] = orgList.get(i).getKey();
            temp[i][5] = orgList.get(i).getId();
        }

        return new R(temp, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
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
