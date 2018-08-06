package com.rsp.rsp.controller;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer page,
                              @RequestParam(value ="length",defaultValue ="10")Integer size,
                              OrgQuery orgQuery){
//        return orgService.findOrgCriteria(page,size,orgQuery);
        List<Org> orgList = orgService.findOrgNoCriteria(page,size).getContent();
        Object[][] temp = new Object[orgList.size()][2];
        for (int i = 0;i<orgList.size();i++){
            temp[i][0] = orgList.get(i).getId();
            temp[i][1] = orgList.get(i).getName();
        }
//        return orgService.findOrgNoCriteria(page,size);

        return new R(temp);
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
