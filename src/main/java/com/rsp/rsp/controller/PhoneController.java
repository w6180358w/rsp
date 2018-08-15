package com.rsp.rsp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.domain.bean.ParamsBean;
import com.rsp.rsp.service.FormulaService;
import com.rsp.rsp.service.OrgService;

/**
 * 大类
 * @author sjb
 */
@RestController
@RequestMapping()
public class PhoneController {
	
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private OrgService orgService;
	
    @RequestMapping("/phone")
    public ModelAndView phone(){
        return new ModelAndView("phone.html");
    }
    
    @RequestMapping("/form/{type}")
    public ModelAndView information(@PathVariable(name="type") String type,Model model){
        return new ModelAndView("form.html");
    }
    
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public ModelAndView list(@ModelAttribute FormulaBean bean, Model model) throws Exception{
    	try {
			List<String> keys = bean.getKeys();
			List<String> paramKeys = new ArrayList<String>();
			List<ParamsBean> params = new ArrayList<ParamsBean>();
			for (String key : keys) {
				String[] p = key.split("_rsp_");
				ParamsBean param = new ParamsBean();
				param.setKey(p[0]);
				param.setValue(Double.parseDouble(p[1]));
				
				params.add(param);
				paramKeys.add(p[0]);
			}
			bean.setKeys(paramKeys);
			bean.setParam(params);
			List<Org> list = this.formulaService.filter(bean);
			model.addAttribute("orgs",list);
			model.addAttribute("type",bean.getType());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("type",bean.getType());
			model.addAttribute("error","查询错误!");
			return new ModelAndView("form.html");
		}
        return new ModelAndView("list.html");
    }
    
    @RequestMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name="id") Long id,Model model){
    	Org org = this.orgService.findById(id);
    	model.addAttribute("org", org);
        return new ModelAndView("list-details.html");
    }
    
}
