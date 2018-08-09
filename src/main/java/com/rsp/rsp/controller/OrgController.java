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
    public R queryAll(@RequestParam(value ="iDisplayStart",defaultValue ="0")Integer start,
                              @RequestParam(value ="iDisplayLength",defaultValue ="10")Integer size,
                              OrgQuery orgQuery,Integer draw){
        Page<Org> pageInfo = orgService.findOrgCriteria(start,size,orgQuery);
        List<Org> orgList = pageInfo.getContent();
        Object[][] temp = new Object[orgList.size()][14];
        for (int i = 0;i<orgList.size();i++){
            temp[i][0] = orgList.get(i).getId();
            temp[i][1] = orgList.get(i).getId();
            temp[i][2] = orgList.get(i).getName();
            temp[i][3] = orgList.get(i).getLimit();
            temp[i][4] = orgList.get(i).getTerm();
            temp[i][5] = orgList.get(i).getInterestRate();
            temp[i][6] = orgList.get(i).getRequirements();
            temp[i][7] = orgList.get(i).getMaterial();
            temp[i][8] = orgList.get(i).getLogo();
            temp[i][9] = orgList.get(i).getDesc();
            temp[i][10] = orgList.get(i).getContacts();
            temp[i][11] = orgList.get(i).getPhone();
            temp[i][12] = orgList.get(i).getStrengths();
            temp[i][13] = orgList.get(i).getId();
        }

        return new R(temp, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
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
