package com.ivan.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterAppsService;
import org.ivan.api.system.SysUcenterFunctionService;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.admin.SysUcenterFunction;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
/**
 * 菜单管理
 * @author 周立波
 *
 */
@RequestMapping("/function/*")
@Controller
public class SysAdminFunctionController extends BaseManagerController<SysUcenterFunction>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAdminController.class);
    
    @Reference
    private SysUcenterFunctionService sysUcenterFunctionService;
    
    @Reference
    private SysUcenterAppsService sysUcenterAppsService;
    
    /**
     * @author buyuer
     * @Title: toList
     * @Description: 去菜单列表页面
     */
    @RequestMapping("/gomenulist")
    public ModelAndView toList(@RequestParam Map<String, Object> map) {
    	System.out.println(map);
        ModelAndView view = new ModelAndView("/menu/menu-list");
        PageObject<SysUcenterFunction> list = sysUcenterFunctionService.listFunctionForAdmin(map);
        
        //查询所有app
        List<SysUcenterApps> apps = sysUcenterAppsService.findAppsAll();
        view.addObject("funList", list);
        view.addObject("apps", apps);
        view.addObject("appIds",0);
        view.addAllObjects(map);
        return view;
    }
    
    
    /**
     * @author buyuer
     * @Title: goAddadm
     * @Description: 去菜单新增页面
     */
    @RequestMapping("/goaddmenu")
    public ModelAndView goAddaMenu(@RequestParam(value ="id", required = false) Long id,@RequestParam(value ="appId", required = false) Integer appId ){
        ModelAndView view = null;
        if (id != null) {
            SysUcenterFunction function = sysUcenterFunctionService.findById(getAdmin().getId(), id);
            view = new ModelAndView("/menu/menu-edit");
            view.addObject("fun", function);
        }else{
            view = new ModelAndView("/menu/menu-add");
            view.addObject("appId", appId);
        }
        //获取已有菜单列表
        List<SysUcenterFunction> funList = sysUcenterFunctionService.findMenuListByAppId(appId);
        view.addObject("funList", funList);
        return view;
    }
    
    /**
     * 
     * @author buyuer
     * @Title: startAdm
     * @Description: 增加菜单
     */
    @ResponseBody
    @RequestMapping("/addmenu")
    public Map<String, Object> addMenu(@RequestParam Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterFunctionService.addMenu(getAdmin().getId(), map);
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
     * @Description: 启用菜单
     */
    @ResponseBody
    @RequestMapping("/startmenu")
    public Map<String, Object> startMenu(@RequestParam("id") Long id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterFunctionService.startFunction(getAdmin().getId(), id);
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
     * @Description: 禁用菜单
     */
    @ResponseBody
    @RequestMapping("/stopmenu")
    public Map<String, Object> stopMenu(@RequestParam("id") Long id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterFunctionService.stopFunction(getAdmin().getId(), id);
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
     * @Description: 删除菜单
     */
    @ResponseBody
    @RequestMapping("/updmenu")
    public Map<String, Object> updMenu(@RequestParam Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterFunctionService.updMenu(getAdmin().getId(), map);
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
     * @Description: 删除菜单
     */
    @ResponseBody
    @RequestMapping("/delmenu")
    public Map<String, Object> delMenu(@RequestParam("id") Long id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterFunctionService.delFunction(id);
            resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            LOGGER.error("服务器异常", e);
            resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
        return resultMap;
    }
    
    
}

