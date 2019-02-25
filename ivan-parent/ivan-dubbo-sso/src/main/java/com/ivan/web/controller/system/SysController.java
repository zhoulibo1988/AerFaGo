package com.ivan.web.controller.system;

import org.ivan.api.it.ItDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
@RequestMapping("/sys/*")
public class SysController {
	@Reference
	private ItDataService itDataService;
    
    @RequestMapping(value = "/login",method = { RequestMethod.GET,RequestMethod.POST })
    public ModelAndView gotoLogin() {
        ModelAndView v = new ModelAndView("/login/index");
        return v;
    }
    
    @RequestMapping(value = "/error", method = { RequestMethod.GET,RequestMethod.POST })
    public ModelAndView error() {
        ModelAndView v = new ModelAndView("/404");
        return v;
    }
}
