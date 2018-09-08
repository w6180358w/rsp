package com.rsp.rsp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rsp.rsp.domain.R;
import com.rsp.rsp.service.StatService;

import net.sf.json.JSONObject;

/**
 * 大类
 * @author sjb
 */
@RestController
@RequestMapping("/stat")
public class StatController {
    @Autowired
    private StatService statService;

    @RequestMapping()
    public ModelAndView category(){
        return new ModelAndView("stat.html");
    }

    @RequestMapping("/query")
    public R queryAll(@RequestParam(name ="start",defaultValue ="0")Long start,
    		@RequestParam(name ="end",defaultValue ="0")Long end,@RequestParam(name ="city") String city,@RequestParam(name ="group") String group){
    	JSONObject json = new JSONObject();
    	json.put("org", this.statService.statXOrg(start, end, city, group));
    	json.put("time", this.statService.statXTime(start, end, city, group));
        return new R(Arrays.asList(json));
    }

}
