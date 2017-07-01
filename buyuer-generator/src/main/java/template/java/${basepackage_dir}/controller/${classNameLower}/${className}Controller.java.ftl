package ${basepackage}.controller.${table.classNameLower};

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ${basepackage}.controller.BaseController;
import ${basepackage}.entity.${table.className};
import ${basepackage}.service.${table.className}Service;
import c${basepackage}.util.PageObject;
import ${basepackage}.util.ParameterEunm;
import ${basepackage}.util.ReMessage;

@Controller
@RequestMapping("/${table.className}/*")
public class ${table.className}Controller extends BaseController<${table.className}> {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ${table.className}Service service;

	@Autowired
	public ${table.className}Controller(${table.className}Service service) {
		setBaseService(service);
	}

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/pages/${table.classNameLower}/list.jsp";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Map<String, Object> map) {
		logger.debug(map);
		ModelAndView andView = new ModelAndView();
		andView.addAllObjects(map);
		andView.setViewName("/pages/${table.classNameLower}/edit.jsp");
		logger.debug(andView);
		return andView;
	}
}
