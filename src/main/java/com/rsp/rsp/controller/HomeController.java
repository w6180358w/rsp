package com.rsp.rsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 入口
 * @author sjb
 */
@Controller
@RequestMapping()
public class HomeController {

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    @RequestMapping("dashboard.html")
    public String dashboard(){
        return "dashboard-backup.html";
    }
    @RequestMapping("charts.html")
    public String charts(){
        return "charts.html";
    }
    @RequestMapping("calendar.html")
    public String calendar(){
        return "calendar.html";
    }
    @RequestMapping("files.html")
    public String files(){
        return "files.html";
    }
    @RequestMapping("table.html")
    public String table(){
        return "table.html";
    }
    @RequestMapping("form_layouts.html")
    public String form_layouts(){
        return "form_layouts.html";
    }
    @RequestMapping("form_elements.html")
    public String form_elements(){
        return "form_elements.html";
    }
    @RequestMapping("widgets.html")
    public String widgets(){
        return "widgets.html";
    }
    @RequestMapping("typography.html")
    public String typography(){
        return "typography.html";
    }
    @RequestMapping("grids.html")
    public String grids(){
        return "grids.html";
    }
    @RequestMapping("gallery.html")
    public String gallery(){
        return "gallery.html";
    }
    @RequestMapping("error.html")
    public String error(){
        return "error.html";
    }
    @RequestMapping("icons.html")
    public String icons(){
        return "icons.html";
    }
}
