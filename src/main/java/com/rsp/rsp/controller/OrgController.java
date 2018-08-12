package com.rsp.rsp.controller;

import com.rsp.rsp.config.PropertiesConif;
import com.rsp.rsp.domain.R;
import com.rsp.rsp.domain.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 机构
 * @author sjb
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;
    @Autowired
    private PropertiesConif conif;

    @RequestMapping()
    public ModelAndView dashboard(){
        return new ModelAndView("org.html");
    }

    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam(value ="start",defaultValue ="0")Integer start,
                              @RequestParam(value ="pageSize",defaultValue ="10")Integer size,
                              OrgQuery orgQuery,Integer draw){
        Page<Org> pageInfo = orgService.findOrgCriteria(start,size,orgQuery);
        List<Org> list = pageInfo.getContent().stream()
                .peek(x->{
                    x.setLimitString(x.getLimitMin()+"-"+x.getLimitMax()+"万");
                    String rateStr;
                    if(StringUtils.isEmpty(x.getInterestRateMin()) && StringUtils.isEmpty(x.getInterestRateMax())){
                        //都没值
                        rateStr = "";
                    }else if(StringUtils.hasText(x.getInterestRateMin()) && StringUtils.hasText(x.getInterestRateMax())){
                        //都有值
                        rateStr = x.getInterestRateMin()+"%-"+x.getInterestRateMax()+"%";
                    }else {
                        //有一个值
                        rateStr = StringUtils.hasText(x.getInterestRateMin())?x.getInterestRateMin()+"%":x.getInterestRateMax()+"%";
                    }
                    x.setInterestRateString(rateStr);
                }).collect(Collectors.toList());
        return new R(list, (int) pageInfo.getTotalElements(), (int) pageInfo.getTotalElements(),draw,"");
    }

    @RequestMapping("add")
    public ModelAndView addOrg(Long id, Model model){
        Org org;
        if(null!=id){
            org = orgService.findById(id);
        }else{
            org = new Org();
            org.setId(0L);
        }
        model.addAttribute("org",org);
        return new ModelAndView("addOrg.html");
    }

    @PostMapping("/save")
    public String save(Org org, MultipartFile file){
        try {
            //文件名称
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String temp = fileName.substring(fileName.lastIndexOf("."));
            // 设置存放图片文件的路径 +
            String path = conif.getLocalPath()+StringUtils.cleanPath(org.getName())+temp;
            file.transferTo(new File(path));
            org.setLogo(path);
            orgService.save(org);
            return "success";
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(Org org, MultipartFile file){
        try {
            String classpath = ResourceUtils.getURL("classpath:").getPath();
            //文件名称
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String temp = fileName.substring(fileName.lastIndexOf("."));
            // 设置存放图片文件的路径 +
            String path = classpath + conif.getLocalPath() +StringUtils.cleanPath(org.getName())+temp;
            File logo = new File(classpath + conif.getLocalPath());
            if(!logo.exists()){
                logo.mkdirs();
            }
            file.transferTo(new File(path));
            org.setLogo("../"+path.substring(path.lastIndexOf("logo")));
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
