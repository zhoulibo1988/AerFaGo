package com.ivan.dubbo.dao.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.wx.WeixinOneMenu;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author cyl
 * @version 
 */
public interface WeixinOneMenuMapper extends BaseMapper<WeixinOneMenu>{
	public void insertByEntity(WeixinOneMenu weixinOneMenu);
	public void deleteByEntity(Map<String,Object> map);
	public void updateByEntity(Map<String,Object> map);
	public void updateByEntity(WeixinOneMenu weixinOneMenu);
	public List<WeixinOneMenu> selectByObject (Map<String,Object> map);
	public List<WeixinOneMenu> selectByObject2 (Map<String,Object> map);
	public WeixinOneMenu selectSingle(Map<String,Object> map);
	public WeixinOneMenu selectSingle(WeixinOneMenu weixinOneMenu);
}