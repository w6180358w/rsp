package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * 机构
 * @author sjb
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping()
    public ModelAndView dashboard(){
        return new ModelAndView("org.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                              @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                              OrgQuery orgQuery,Integer draw){
        Page<Org> pageInfo = orgService.findOrgCriteria(start,size,orgQuery);
        return new R(pageInfo.getContent(), (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView addOrg(Long id, Model model){
        Org org;
        if(null!=id){
            org = orgService.findById(id);
        }else{
            org = new Org();
            org.setId(0);
        }
        model.addAttribute("org",org);
        return new ModelAndView("addOrg.html");
    }

    @PostMapping("/save")
    public String save(Org org){
        try {
            orgService.save(org);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Org org){
        try {
            orgService.update(org);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value ="id" )Long id){
        try {
            orgService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
