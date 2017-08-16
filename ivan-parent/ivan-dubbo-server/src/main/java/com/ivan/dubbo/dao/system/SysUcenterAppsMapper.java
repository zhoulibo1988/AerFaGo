package com.ivan.dubbo.dao.system;

import java.util.Map;

import org.ivan.entity.admin.SysUcenterApps;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author buyuer
 * @version 
 */
public interface SysUcenterAppsMapper extends BaseMapper<SysUcenterApps>{
	public void updateByEntity(Map<String,Object> map);
	public void deleteByEntity(Map<String,Object> map);
}