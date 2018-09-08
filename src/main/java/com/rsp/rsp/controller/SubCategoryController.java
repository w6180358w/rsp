package com.rsp.rsp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.Type;
import com.rsp.rsp.domain.bean.SubCategoryBean;
import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.service.CategoryService;
import com.rsp.rsp.service.SubCategoryService;
import com.rsp.rsp.service.TypeService;

import net.sf.json.JSONObject;

/**
 * 小类
 * @author sjb
 */
@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypeService typeSerivce;

    @RequestMapping()
    public ModelAndView subCategory(){
        return new ModelAndView("subCategory.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                      @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                      SubCategoryQuery subCategoryQuery,Integer draw){
        Page<SubCategory> pageInfo = subCategoryService.findSubCategoryCriteria(start,size,subCategoryQuery);
        List<SubCategoryBean> result = new ArrayList<>();
        List<SubCategory> subList = pageInfo.getContent();
        //大类ID集合
        Set<Long> cateIdSet = new HashSet<>();
        //类型唯一标识集合
        List<String> keyList = new ArrayList<>();
        Map<Long,Category> cateMap = new HashMap<>();
        Map<String,Type> cateTypeMap = new HashMap<>();
        //组装大类ID
        for (SubCategory sub : subList) {
        	cateIdSet.add(sub.getCategoryId());
		}
        //根据大类ID集合查询大类
        List<Category> categoryList = this.categoryService.inId(cateIdSet);
        //组装类型唯一标识集合
        for (Category cate : categoryList) {
        	keyList.add(cate.getType());
        	cateMap.put(cate.getId(), cate);
		}
        //根据类型唯一标识集合查询类型
        List<Type> typeList = this.typeSerivce.inKey(keyList);
        for (Type type : typeList) {
        	cateTypeMap.put(type.getKey(), type);
		}
        
        //组装返回对象
        for (SubCategory sub : subList) {
        	SubCategoryBean bean = (SubCategoryBean) JSONObject.toBean(JSONObject.fromObject(sub), SubCategoryBean.class);
        	Category cate = cateMap.get(sub.getCategoryId());
        	if(cate!=null) {
        		bean.setCategoryName(cate.getName());
        		Type type = cateTypeMap.get(cate.getType());
        		bean.setType(type);
        	}
        	result.add(bean);
		}
        return new R(result, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView addSubCategory(Long id, Model model){
        SubCategory subCategory;
        List<Type> typeList;
        String group = "wdy",city = "北京市",type = "";
        if(null!=id){
            subCategory = subCategoryService.findById(id);
            Category cate = this.categoryService.findById(subCategory.getCategoryId());
            type = cate.getType();
            typeList = this.typeSerivce.key(type);
            if(typeList!=null && !typeList.isEmpty()) {
            	group = typeList.get(0).getGroup();
            	city = typeList.get(0).getCityName();
            }else {
            	city = "";
            }
        }else{
            subCategory = new SubCategory();
            subCategory.setId(0L);
            subCategory.setCategoryId(0L);
            typeList = new ArrayList<>();
        }
        model.addAttribute("group",group);
        model.addAttribute("type",type);
        model.addAttribute("cityName",city);
        model.addAttribute("typeList",typeList);
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
