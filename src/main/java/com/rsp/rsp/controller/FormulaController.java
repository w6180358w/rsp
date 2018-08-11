package com.rsp.rsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.bean.CategoryBean;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.service.FormulaService;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公式
 * @author sjb
 */
@RestController
@RequestMapping("/formula")
public class FormulaController {
    @Autowired
    private FormulaService formulaService;

    @RequestMapping()
    public ModelAndView formula(){
        return new ModelAndView("formula.html");
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public R queryAll(){
        List<Formula> orgList = this.formulaService.findAll();
        return new R(orgList);
    }

    @PostMapping("/save")
    public String save(Formula Formula){
        try {
        	formulaService.save(Formula);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Formula Formula){
        try {
        	formulaService.update(Formula);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        try {
        	formulaService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
    
    @PostMapping("/filter")
    public R filter(@RequestBody FormulaBean bean) throws Exception{
    	List<Org> orgList = this.formulaService.filter(bean);
        return new R(orgList);
    }
    
    @PostMapping("/tableFilter")
    public R tableFilter(@RequestBody FormulaBean bean) throws Exception{
    	List<JSONObject> orgList = this.formulaService.tableFilter(bean);
        return new R(orgList, orgList.size(), orgList.size(),1,null);
    }
    
    @GetMapping("/columns/{type}")
    public R columns(@PathVariable("type")String type) throws Exception{
    	List<CategoryBean> list = this.formulaService.columns(type);
        return new R(list);
    }
}
