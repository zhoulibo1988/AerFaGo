package com.ivan.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.utils.ValidatorUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseManagerController<T> {
	 /**
     * 
     * @author buyuer
     * @Title: getAdmin
     * @Description: 获取admin
     */
    public SysUcenterAdmin getAdmin() {
        SysUcenterAdmin admin = (SysUcenterAdmin) getSessionAttr("admin");
        if (admin != null) {
            return admin;
        }
        return null;
    }

    /**
     * 
     * @author buyuer
     * @Title: setAdmin
     * @Description: 将admin存入session
     */
    public void setAdmin(SysUcenterAdmin admin) {
        getSession().setAttribute("admin", admin);
    }
    
    /**
     * 获取session
     * 
     * @param code
     * @return
     */
    protected Object getSessionAttr(String code) {
        HttpSession session = getSession();
        if (session != null && ValidatorUtil.isNotEmpty(code)) {
            return getSession().getAttribute(code);
        } else {
            return null;
        }
    }

    /**
     * 
     * 
     * @author    Buyuer
     * @version   V0.1 2015年10月30日[版本号, YYYY-MM-DD]
     * @see       获取session方法
     * @parm
     */
    protected HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }
}
