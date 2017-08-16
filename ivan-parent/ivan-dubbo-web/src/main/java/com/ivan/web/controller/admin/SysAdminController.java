package com.ivan.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ivan.api.system.SysUcenterAdminService;
import org.ivan.api.system.SysUcenterAppsService;
import org.ivan.api.system.SysUcenterFunctionService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.utils.MapObjectUtil;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.PasswordEncoder;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.utils.ReadPro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
@RequestMapping("sysAdmin")
public class SysAdminController extends BaseManagerController<SysUcenterAdmin>{

	private static final Logger LOGGER = LoggerFactory.getLogger(SysAdminController.class);

	@Reference
	private SysUcenterAdminService sysUcenterAdminService;

	@Reference
	private SysUcenterAppsService sysUcenterAppsService;
	@Reference
	private SysUcenterFunctionService sysUcenterFunctionService;

	/**
	 * 
	 * @author buyuer
	 * @Title: login
	 * @Description: 登录方法
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isNotNull = MapObjectUtil.checkObject(new String[] { "userName", "password", "code"}, map);
		if(isNotNull){
			// 此处默认有值
			try {
				String username = map.get("userName").toString();
				String password = map.get("password").toString();
				String code = map.get("code").toString();
				String oldCode = getSessionAttr("code").toString();
				if (code != null && code.equalsIgnoreCase(oldCode)) {
					// MD5加密
					password = PasswordEncoder.getPassword(password);
					SysUcenterAdmin admin = sysUcenterAdminService.loginAdmin(username, password);
					if (admin != null) {
						setAdmin(admin);
						resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, "sysAdmin/menu.do");
					} else {
						resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, "请输入正确账号和密码!");
					}
				} else {
					resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, "请输入正确验证码!");
				}
			} catch (Exception e) {
				LOGGER.error("登录失败", e);
				resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, "请输入正确账号和密码!");
			}
		}else{
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, null);
		}
		return resultMap;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		ModelAndView andView = new ModelAndView("login");
		getSession().invalidate();
		return andView;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: menu_admin
	 * @Description:跳转到菜单中心
	 */
	@RequestMapping(value = "/menu")
	public ModelAndView menu_admin(@RequestParam Map<String, Object> map, ModelAndView modelAndView) {
		ModelAndView mv = null;
		try {
			SysUcenterAdmin admin = getAdmin();
			if (admin.getAdmType().equals("SUPER")) {
				mv = new ModelAndView("center/index_admin");
			} else {
				// 此处查询用户的权限列表
				mv = getMenuNormal(mv, admin.getId());
			}
		} catch (Exception e) {
			LOGGER.error("出现异常", e);
		}
		return mv;
	}

	private ModelAndView getMenuNormal(ModelAndView view, Long userId) {
		view = new ModelAndView("center/index");
		List<Map<String, Object>> functions = sysUcenterFunctionService.listFunctionForNormal(userId, ReadPro.getValue("app_code"));
		view.addObject("functions", functions);
		return view;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: appList
	 * @Description: 应用列表
	 */
	@RequestMapping("/goapplist")
	public ModelAndView goapplist(@RequestParam Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("app/app-list");
		PageObject<SysUcenterApps> pageObject = sysUcenterAppsService.Pagequery(map);
		mv.addObject("list", pageObject);
		return mv;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: goAddApp
	 * @Description: 去添加应用页面
	 */
	@RequestMapping("/goaddapp")
	public ModelAndView goAddApp(@RequestParam(value = "id", required = false) Integer id) {
		ModelAndView mv = null;
		if (id != null) {
			mv = new ModelAndView("app/app-edit");
			SysUcenterApps apps = sysUcenterAppsService.findById(id);
			mv.addObject("app", apps);
		} else {
			mv = new ModelAndView("app/app-add");
		}

		return mv;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: doAddApp
	 * @Description: 保存应用
	 */
	@ResponseBody
	@RequestMapping("/addapp")
	public Map<String, Object> doAddApp(@RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			map.put("createUserBy", getAdmin().getId());
			sysUcenterAppsService.add(map);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
			LOGGER.error(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: doAddApp
	 * @Description: 修改应用
	 */
	@ResponseBody
	@RequestMapping("/updapp")
	public Map<String, Object> updapp(@RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			map.put("updateUserBy", getAdmin().getId());
			sysUcenterAppsService.update(map);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
			LOGGER.error(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: doAddApp
	 * @Description: 删除应用
	 */
	@ResponseBody
	@RequestMapping("/delapp")
	public Map<String, Object> delapp(@RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUcenterAppsService.delete(map);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
			LOGGER.error(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: toAdminList
	 * @Description: 去管理员列表
	 */
	@RequestMapping("/goadminlist")
	public ModelAndView toAdminList(@RequestParam Map<String, Object> map) {
		ModelAndView view = new ModelAndView("admin/adm-list");
		PageObject<SysUcenterAdmin> list = sysUcenterAdminService.Pagequery(map);
		view.addObject("list", list);
		return view;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: goAddadm
	 * @Description: 去管理员新增页面
	 */
	@RequestMapping("/goaddadm")
	public ModelAndView goAddadm(@RequestParam(value = "id", required = false) Long id) {
		ModelAndView view = null;
		if (id != null) {
			view = new ModelAndView("admin/adm-edit");
			SysUcenterAdmin admin = sysUcenterAdminService.findById(id);
			view.addObject("adm", admin);
		} else {
			view = new ModelAndView("admin/adm-add");
		}
		return view;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: startAdm
	 * @Description: 启用管理员
	 */
	@ResponseBody
	@RequestMapping("/startadm")
	public Map<String, Object> startAdm(@RequestParam("id") Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUcenterAdminService.startAdmin(id);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			LOGGER.error("服务器异常", e);
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: startAdm
	 * @Description: 禁用管理员
	 */
	@ResponseBody
	@RequestMapping("/stopadm")
	public Map<String, Object> stopAdm(@RequestParam("id") Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUcenterAdminService.stopAdmin(id);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			LOGGER.error("服务器异常", e);
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: startAdm
	 * @Description: 删除管理员
	 */
	@ResponseBody
	@RequestMapping("/deladm")
	public Map<String, Object> delAdm(@RequestParam("id") Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUcenterAdminService.delAdmin(id);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			LOGGER.error("服务器异常", e);
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: startAdm
	 * @Description: 添加管理员
	 */
	@ResponseBody
	@RequestMapping("/addadm")
	public Map<String, Object> addAdm(@RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUcenterAdminService.addAdmin(map);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			LOGGER.error("服务器异常", e);
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}

	/**
	 * 
	 * @author buyuer
	 * @Title: startAdm
	 * @Description: 修改管理员信息
	 */
	@ResponseBody
	@RequestMapping("/updadm")
	public Map<String, Object> updAdm(@RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String passWrod = String.valueOf(map.get("admPassword"));
			// 如果前端传的加密值与预设的加密值一样；证明用户没有修改密码；故设为null;
			if (passWrod.equals("4c6e33e575da86adc6df9d85790c75ce")) {
				map.put("admPassword", null);
			}
			sysUcenterAdminService.update(map);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		} catch (Exception e) {
			LOGGER.error("服务器异常", e);
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
}
