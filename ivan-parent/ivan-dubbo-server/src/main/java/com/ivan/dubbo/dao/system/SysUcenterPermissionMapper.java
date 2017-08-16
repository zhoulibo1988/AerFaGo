package com.ivan.dubbo.dao.system;

import java.util.List;

import org.ivan.entity.admin.SysUcenterPermission;

import com.ivan.dubbo.dao.BaseMapper;


/**
 * @author buyuer
 * @version
 */
public interface SysUcenterPermissionMapper extends BaseMapper<SysUcenterPermission> {

    /**
     * 
     * @author buyuer
     * @Title: selectFunByRole
     * @Description: 根据角色查询权限列表
     * @return
     */
    List<Long> selectFunByRole(Long id);
}