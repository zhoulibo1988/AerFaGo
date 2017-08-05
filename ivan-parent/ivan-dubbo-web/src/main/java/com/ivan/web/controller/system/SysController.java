package com.ivan.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/*")
public class SysController {
    
    
    @RequestMapping(value = "/login",method = { RequestMethod.GET,RequestMethod.POST })
    public ModelAndView gotoLogin() {
        ModelAndView v = new ModelAndView("/login");
        return v;
    }
    
    @RequestMapping(value = "/error", method = { RequestMethod.GET,RequestMethod.POST })
    public ModelAndView error() {
        ModelAndView v = new ModelAndView("/404");
        return v;
    }
}
