package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.ContactsQuery;
import com.rsp.rsp.domain.Contacts;
import com.rsp.rsp.service.ContactsService;
import com.rsp.rsp.service.OrgService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String save(Contacts contacts){
        try {
        	contactsService.save(contacts);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Contacts contacts){
        try {
        	contactsService.update(contacts);
            return "success";
        }catch (Exception e){
            return "error";
        }
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
    
    @RequestMapping("/org/{orgId}")
    public R queryAll(@PathVariable(name="orgId")Long orgId){
        Map<String, List<Contacts>> map = contactsService.findByOrg(orgId);
        return new R(Arrays.asList(map));
    }
}
