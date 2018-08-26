package com.ivan.dubbo.dao.vide;

import java.util.List;
import java.util.Map;

import org.ivan.entity.vide.TbMedia;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author cyl
 * @version 
 */
public interface TbMediaMapper extends BaseMapper<TbMedia>{
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<TbMedia> selectByObject(Map<String, Object> map);
}