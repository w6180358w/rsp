package com.rsp.rsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.Contacts;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.ContactsQuery;
import com.rsp.rsp.service.ContactsService;
import com.rsp.rsp.service.OrgService;

/**
 * 联系人
 * @author sjb
 */
@RestController
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    private ContactsService contactsService;
    
    @Autowired
    private OrgService orgService;

    @RequestMapping()
    public ModelAndView Contacts(){
        return new ModelAndView("contacts.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                                   @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                                   ContactsQuery ContactsQuery,Integer draw){
        Page<Contacts> pageInfo = contactsService.findContactsCriteria(start,size,ContactsQuery);
        return new R(pageInfo.getContent(), (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView add(Long id, Model model){
        Contacts contacts;
        if(null!=id){
            contacts = contactsService.findById(id);
        }else{
        	contacts = new Contacts();
        	contacts.setId(0L);
        }
        model.addAttribute("contacts",contacts);
        model.addAttribute("orgList",this.orgService.findAll());
        return new ModelAndView("addContacts.html");
    }
    @PostMapping("/save")
    public R save(Contacts contacts){
    	R r = new R(true);
        try {
        	Contacts val = contactsService.valOrder(contacts);
        	if(val!=null) {
        		 r.setError("添加失败，与["+val.getOrgName()+"--"+val.getName()+"]序号相同!");
            	r.setSuccess(false);
	       	}else {
	       		contactsService.save(contacts);
	       	}
        }catch (Exception e){
        	e.printStackTrace();
            r.setError("添加失败，请联系管理员!");
            r.setSuccess(false);
        }
        return r;
    }

    @PostMapping("/update")
    public R update(Contacts contacts){
    	R r = new R(true);
        try {
        	Contacts val = contactsService.valOrder(contacts);
        	if(val!=null) {
        		 r.setError("更新失败，与["+val.getOrgName()+"--"+val.getName()+"]序号相同!");
                 r.setSuccess(false);
        	}else {
        		contactsService.update(contacts);
        	}
        }catch (Exception e){
        	e.printStackTrace();
            r.setError("更新失败，请联系管理员!");
            r.setSuccess(false);
        }
        return r;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value ="id" )Long id){
        try {
        	contactsService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
