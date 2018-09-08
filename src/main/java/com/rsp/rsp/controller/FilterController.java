package com.rsp.rsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.bean.CategoryBean;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.service.FormulaService;

import net.sf.json.JSONObject;

/**
 * 公式
 * @author sjb
 */
@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    private FormulaService formulaService;

    @RequestMapping("")
    public ModelAndView formula(){
        return new ModelAndView("filter.html");
    }
    /**
     * 手机端  查询返回org对象
     * @param bean
     * @return
     * @throws Exception
     */
    @PostMapping("/filter")
    public R filter(@RequestBody FormulaBean bean) throws Exception{
    	List<Org> orgList = this.formulaService.filter(bean);
        return new R(orgList);
    }
    /**
     * pc端  返回分数对象
     * @param bean
     * @return
     * @throws Exception
     */
    @PostMapping("/tableFilter")
    public R tableFilter(@RequestBody FormulaBean bean) throws Exception{
    	List<JSONObject> orgList = this.formulaService.tableFilter(bean);
        return new R(orgList, orgList.size(), orgList.size(),bean.getDraw(),null);
    }
    /**
     * 获取列头
     * @param type
     * @return
     * @throws Exception
     */
    @GetMapping("/columns/{typeId}")
    public R columns(@PathVariable("typeId")Long typeId) throws Exception{
    	List<CategoryBean> list = this.formulaService.columns(typeId);
        return new R(list);
    }
}
