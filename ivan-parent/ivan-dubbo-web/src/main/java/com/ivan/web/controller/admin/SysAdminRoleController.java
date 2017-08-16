
package com.ivan.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterAdminRoleService;
import org.ivan.api.system.SysUcenterAppsService;
import org.ivan.api.system.SysUcenterFunctionService;
import org.ivan.api.system.SysUcenterPermissionService;
import org.ivan.api.system.SysUcenterRoleService;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.admin.SysUcenterRole;
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
 * 管理员角色控制器
 * @author 周立波
 *
 */
@RequestMapping("/roles/*")
@Controller
public class SysAdminRoleController extends BaseManagerController<SysUcenterRole> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAdminRoleController.class);

    @Reference
    private SysUcenterAdminRoleService sysUcenterAdminRoleService;

    @Reference
    private SysUcenterRoleService sysUcenterRoleService;

    @Reference
    private SysUcenterAppsService sysUcenterAppsService;

    @Reference
    private SysUcenterFunctionService sysUcenterFunctionService;

    @Reference
    private SysUcenterPermissionService sysUcenterPermissionService;

    /**
     * 
     * @author buyuer
     * @Title: toRoleList
     * @Description: 到角色列表
     */
    @RequestMapping("/torolelist")
    public ModelAndView toRoleList(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("/role/role-list");
        PageObject<Map<String, Object>> pageObject = sysUcenterRoleService.pageQueryByObject_Map(map);
        andView.addObject("list", pageObject);
        return andView;
    }

    /**
     * 
     * @author buyuer
     * @Title: toRoleList
     * @Description: 到角色列表
     */
    @RequestMapping("/goaddrole")
    public ModelAndView goAddRole(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView andView = null;
        if (id != null) {
            SysUcenterRole role = sysUcenterRoleService.findById(id);
            andView = new ModelAndView("/role/edit-role");
            andView.addObject("role", role);
        } else {
            andView = new ModelAndView("/role/add-role");
        }
        // 查询出所有项目装载到这里
        List<SysUcenterApps> appList = sysUcenterAppsService.query(new HashMap<String, Object>());
        andView.addObject("appList", appList);
        return andView;
    }

    /**
     * 
     * @author buyuer
     * @Title: addRole
     * @Description: 增加角色
     */
    @RequestMapping("/updrole")
    @ResponseBody
    public Map<String, Object> updRole(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterRoleService.updRole(map, getAdmin().getId());
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
     * @Title: addRole
     * @Description: 增加角色
     */
    @RequestMapping("/addrole")
    @ResponseBody
    public Map<String, Object> addRole(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterRoleService.addRole(map, getAdmin().getId());
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
     * @Title: stopRole
     * @Description: 锁定角色
     * @param id  角色id
     */
    @RequestMapping("/stoprole")
    @ResponseBody
    public Map<String, Object> stopRole(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterRoleService.stopRole(id, getAdmin().getId());
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
     * @Title: stopRole
     * @Description: 解锁角色
     * @param id 角色id
     */
    @RequestMapping("/startrole")
    @ResponseBody
    public Map<String, Object> startRole(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterRoleService.startRole(id, getAdmin().getId());
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
     * @Title: stopRole
     * @Description: 删除角色
     * @param id
     *            角色id
     */
    @RequestMapping("/delrole")
    @ResponseBody
    public Map<String, Object> delRole(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterRoleService.delRole(id);
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
     * @Title: toPermissRole
     * @Description: 打开分配权限页面
     * @param id 角色id
     */
    @RequestMapping("/topermissrole")
    public ModelAndView toPermissRole(@RequestParam(value = "id", required = true) Long id) {
        ModelAndView view = new ModelAndView("/role/permiss-role");
        SysUcenterRole role = sysUcenterRoleService.findById(id);
        List<SysUcenterApps> appList = sysUcenterAppsService.findAppsAll();
        
        //以选中的菜单列表
        List<Long> menuList = sysUcenterPermissionService.selectFunByRole(id);
        //查询所有菜单列表
        List<Map<String, Object>> funMapList = sysUcenterFunctionService.selectFunList();
        
        for (Map<String, Object> map : funMapList) {
            if (menuList.contains(map.get("id"))) {
                map.put("checked", "checked");
            }
        }
        
        view.addObject("funList", funMapList);
        view.addObject("role", role);
        view.addObject("appList", appList);
        return view;
    }

    /**
     * 
     * @author buyuer
     * @Title: stopRole
     * @Description: 增加角色权限
     * @param id  角色id
     */
    @RequestMapping("/addpermissrole")
    @ResponseBody
    public Map<String, Object> addPermissrole(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            LOGGER.info("map : " + map);
            sysUcenterPermissionService.addPermissToRole(map, getAdmin().getId());
            resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            LOGGER.error("服务器异常", e);
            resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
        return resultMap;
    }

    @RequestMapping("/toadminrole")
    public ModelAndView toAdminRole(@RequestParam(value="id", required = true) Long id, @RequestParam(value="userName", required= true) String userName){
        ModelAndView view = new ModelAndView("/admin/admin-role");
        List<SysUcenterApps> appList = sysUcenterAppsService.findAppsAll();
        //获取全部的role
        List<Map<String, Object>> roleList = sysUcenterRoleService.getRoleAll();
        
        //获取原有的
        List<Long> userRoleList = sysUcenterAdminRoleService.findRoleByUser(id);
        for (Map<String, Object> map : roleList) {
            Long role_id = Long.parseLong(map.get("id").toString());
            if (userRoleList.contains(role_id)) {
                map.put("checked", "checked");
            }
        }
        view.addObject("appList", appList);
        view.addObject("roleList", roleList);
        view.addObject("userName", userName);
        view.addObject("id", id);
        return view;
    }
    
    /**
     * 
     * @author buyuer
     * @Title: toAdminAndRole
     * @Description: 到管理员分配角色
     * @param id 管理员id
     */
    @RequestMapping("/addadminrole")
    @ResponseBody
    public Map<String, Object> addAdminRole(@RequestParam Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	sysUcenterAdminRoleService.addAdminRole(map, getAdmin().getId());
            resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            LOGGER.error("服务器异常" ,e);
            resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
        return resultMap;
       
    }
}
