package com.ivan.dubbo.dao.system;

import java.util.Map;

import org.ivan.entity.admin.SysConfigDictionary;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author buyuer
 * @version 
 */
public interface SysConfigDictionaryMapper extends BaseMapper<SysConfigDictionary>{
	void updateByEntity(Map<String,Object> map);
}