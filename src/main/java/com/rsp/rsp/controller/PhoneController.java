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
import com.rsp.rsp.service.ContactsService;
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
    private ContactsService contactsService;
    
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private OrgService orgService;
	
    @RequestMapping("/phone")
    public ModelAndView phone(){
    	
    	
        return new ModelAndView("phone.html");
    }
    
    @RequestMapping("/form/{typeId}")
    public ModelAndView information(@PathVariable(name="typeId") Long typeId,Model model){
        return new ModelAndView("form.html");
    }
    
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public ModelAndView list(@ModelAttribute FormulaBean bean, Model model) throws Exception{
    	try {
			List<String> keys = bean.getKeys();
			List<Long> ids = new ArrayList<Long>();
			List<ParamsBean> params = new ArrayList<ParamsBean>();
			for (String key : keys) {
				String[] p = key.split("_rsp_");
				ParamsBean param = new ParamsBean();
				param.setKey(p[1]);
				param.setValue(Double.parseDouble(p[2]));
				
				params.add(param);
				ids.add(Long.parseLong(p[0]));
			}
			bean.setIds(ids);
			bean.setParam(params);
			List<Org> list = this.formulaService.filter(bean);
			model.addAttribute("orgs",list);
			model.addAttribute("type",bean.getTypeId());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("type",bean.getTypeId());
			model.addAttribute("error","查询错误!");
			return new ModelAndView("form.html");
		}
        return new ModelAndView("list.html");
    }
    
    @RequestMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name="id") Long id,Model model){
    	Org org = this.orgService.findById(id);
    	model.addAttribute("org", org);
    	model.addAttribute("contacts",contactsService.findByOrg(org.getId()));
        return new ModelAndView("list-details.html");
    }
    
}
