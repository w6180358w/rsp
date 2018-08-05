package com.rsp.rsp.controller;

import com.rsp.rsp.domain.Org;
import com.rsp.rsp.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构
 * @author sjb
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping("/queryAll")
    public List<Org> queryAll(){
        return orgService.findAll();
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        try {
            orgService.delete(id);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }
}
