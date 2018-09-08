package com.rsp.rsp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.Type;
import com.rsp.rsp.domain.bean.CategoryBean;
import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.service.CategoryService;
import com.rsp.rsp.service.TypeService;

import net.sf.json.JSONObject;

/**
 * 大类
 * @author sjb
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypeService typeSerivce;

    @RequestMapping()
    public ModelAndView category(){
        return new ModelAndView("category.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                   @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                   CategoryQuery categoryQuery,Integer draw){
        Page<Category> pageInfo = categoryService.findCategoryCriteria(start,size,categoryQuery);
        List<CategoryBean> result = new ArrayList<>();
        //根据大类ID集合查询大类
        List<Category> categoryList = pageInfo.getContent();
        List<String> keyList = new ArrayList<>();
        Map<String,Type> typeMap = new HashMap<>();
        //组装类型唯一标识集合
        for (Category cate : categoryList) {
        	keyList.add(cate.getType());
		}
        //根据类型唯一标识集合查询类型
        List<Type> typeList = this.typeSerivce.inKey(keyList);
        for (Type type : typeList) {
        	typeMap.put(type.getKey(), type);
		}
        //组装返回对象
        for (Category cate : categoryList) {
        	CategoryBean bean = (CategoryBean) JSONObject.toBean(JSONObject.fromObject(cate),CategoryBean.class);
        	bean.setType(typeMap.get(bean.getType()));
        	result.add(bean);
		}
        
        return new R(result, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView add(Long id, Model model){
        Category category;
        List<Type> typeList;
        String group = "wdy",city = "北京市";
        if(null!=id){
            category = categoryService.findById(id);
            typeList = this.typeSerivce.key(category.getType());
            if(typeList!=null && !typeList.isEmpty()) {
            	group = typeList.get(0).getGroup();
            	city = typeList.get(0).getCityName();
            }else {
            	city = "";
            }
        }else{
            category = new Category();
            category.setId(0L);
            typeList = new ArrayList<>();
        }
        model.addAttribute("group",group);
        model.addAttribute("cityName",city);
        model.addAttribute("category",category);
        model.addAttribute("typeList",typeList);
        model.addAttribute("contextPath","/");
        return new ModelAndView("addCategory.html");
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

    @PostMapping("/delete")
    public String delete(@RequestParam(value ="id" )Long id){
        try {
            categoryService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
    
    @RequestMapping("/type/{type}")
    public R queryAll(@PathVariable(name="type")String type){
        List<Category> list = categoryService.type(type);
        return new R(list);
    }
}
