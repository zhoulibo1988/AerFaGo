package com.ivan.dubbo.dao.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.wx.WeixinAuthCode;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthCodeMapper extends BaseMapper<WeixinAuthCode>{
	public void insertByEntity(WeixinAuthCode weixinAuthCode);
	public void deleteByEntity(Map<String,Object> map);
	public void updateByEntity(Map<String,Object> map);
	public void updateByEntity(WeixinAuthCode weixinAuthCode);
	public List<WeixinAuthCode> selectByObject (Map<String,Object> map);
	public WeixinAuthCode selectSingle(Map<String,Object> map);
	public WeixinAuthCode selectSingle(WeixinAuthCode weixinAuthCode);
}