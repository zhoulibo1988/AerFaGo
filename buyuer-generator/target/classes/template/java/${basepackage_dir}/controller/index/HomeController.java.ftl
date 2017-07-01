package ${basepackage}.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	/**
	 * 主页
	 */
	@RequestMapping("/index")
	public String index() {
		// 转到登录页面
		return "/system/index.jsp";
	}
}
