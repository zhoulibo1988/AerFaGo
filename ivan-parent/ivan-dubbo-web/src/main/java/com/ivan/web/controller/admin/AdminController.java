package com.ivan.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@RequestMapping("login")
	public ModelAndView goToLogin(){
		ModelAndView av=new ModelAndView("admin/login");
		return av;
		
	}
}
