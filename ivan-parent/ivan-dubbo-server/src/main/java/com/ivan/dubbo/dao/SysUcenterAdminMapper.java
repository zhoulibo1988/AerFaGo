package com.ivan.dubbo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysUcenterAdmin;
public interface SysUcenterAdminMapper{
	/*
	 * (非 Javadoc) <p>Title: selectSingle</p> <p>Description: </p>
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @see
	 * cn.iyizhan.treamwork.mapper.BaseMapper#selectSingle(java.lang.Object)
	 */
	/**
	 * 获取总数
	 * @return int
	 */
	int getCount(SysUcenterAdmin t);
	int getCount(Map<String,Object> map);
	
	/**
	 * 插入数据
	 * @param t
	 */
	void insertByEntity(SysUcenterAdmin t);
	
	/**
	 * 删除数据
	 * @param t
	 */
	void deleteByEntity(SysUcenterAdmin t);
	
	/**
	 * 更新数据
	 * @param examuser
	 */
	void updateByEntity(SysUcenterAdmin t);
	
	/**
	 * 单条记录选取
	 * @param t
	 * @return T
	 */
	SysUcenterAdmin selectSingle(SysUcenterAdmin t);
	
 
	/**
	 * 多条选择
	 * @param examuser
	 * @return List<T>
	 */
	List<SysUcenterAdmin> selectByObject(SysUcenterAdmin t);
	
	/**
	 * 分页查询
	 * @param map
	 * @return List<ExamUser>
	 */
	List<SysUcenterAdmin> pageQueryByObject(HashMap<String,Object> map);
}
