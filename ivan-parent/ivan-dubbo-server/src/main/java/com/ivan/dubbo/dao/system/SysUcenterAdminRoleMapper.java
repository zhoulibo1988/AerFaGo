package com.ivan.dubbo.dao.system;

import java.util.List;

import org.ivan.entity.admin.SysUcenterAdminRole;

import com.ivan.dubbo.dao.BaseMapper;
/**
 * @author buyuer
 * @version 
 */
public interface SysUcenterAdminRoleMapper extends BaseMapper<SysUcenterAdminRole>{

    /**
     * @author buyuer
     * @Title: findRoleByUser
     * @Description: 查询当前用户的角色
     */
    List<Long> findRoleByUser(Long userId);
}