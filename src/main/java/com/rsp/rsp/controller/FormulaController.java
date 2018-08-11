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
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.service.FormulaService;

import net.sf.json.JSONObject;

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
    public R queryAll(@RequestBody FormulaBean bean) throws Exception{
    	List<JSONObject> orgList = this.formulaService.findAll(bean);
        return new R(orgList, orgList.size(), orgList.size(),bean.getDraw(),null);
    }

    @PostMapping("/merge")
    public String save(@RequestBody Formula formula){
        try {
        	formulaService.merge(formula);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Formula formula){
        try {
        	formulaService.update(formula);
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
}
