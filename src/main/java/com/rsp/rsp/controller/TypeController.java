package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.TypeQuery;
import com.rsp.rsp.domain.Type;
import com.rsp.rsp.service.TypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 大类
 * @author sjb
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping()
    public ModelAndView Type(){
        return new ModelAndView("type.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                   @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                   TypeQuery typeQuery,Integer draw){
        Page<Type> pageInfo = typeService.findTypeCriteria(start,size,typeQuery);
        return new R(pageInfo.getContent(), (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView add(Long id, Model model){
        Type type;
        if(null!=id){
        	type = typeService.findById(id);
        }else{
        	type = new Type();
        	type.setId(0L);
        }
        model.addAttribute("type",type);
        return new ModelAndView("addType.html");
    }
    @PostMapping("/save")
    public String save(Type type){
        try {
            typeService.save(type);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Type type){
        try {
            typeService.update(type);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value ="id" )Long id){
        try {
            typeService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
    
    @RequestMapping("/city")
    public R queryByCity(@RequestParam(name="city")String type,@RequestParam(name="group")String group){
        List<Type> list = typeService.city(type,group);
        return new R(list);
    }
}
