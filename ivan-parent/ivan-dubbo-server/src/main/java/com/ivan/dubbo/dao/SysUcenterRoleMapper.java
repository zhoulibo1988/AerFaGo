package com.ivan.dubbo.dao;

import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysUcenterRole;

/**
 * @author buyuer
 * @version
 */
public interface SysUcenterRoleMapper extends BaseMapper<SysUcenterRole> {

	/**
	 * 
	 * @author buyuer
	 * @Title: getCount_Map
	 * @Description: 分页查询获取总数
	 */
	int getCount_Map(Map<String, Object> map);

	/**
	 * 
	 * @author buyuer
	 * @Title: pageQueryByObject_Map
	 * @Description: 分页查询返回map
	 */
	List<Map<String, Object>> pageQueryByObject_Map(Map<String, Object> map);

	/**
	 * 
	 * @author buyuer
	 * @Title: queryByObject_Map
	 * @Description: 查询所有的角色
	 */
	List<Map<String, Object>> queryByObject_Map(Map<String, Object> map);
}