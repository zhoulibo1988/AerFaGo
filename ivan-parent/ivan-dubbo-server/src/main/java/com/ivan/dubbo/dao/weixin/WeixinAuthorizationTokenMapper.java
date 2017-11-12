package com.ivan.dubbo.dao.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.wx.WeixinAuthorizationToken;

import com.ivan.dubbo.dao.BaseMapper;
/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthorizationTokenMapper extends BaseMapper<WeixinAuthorizationToken>{
	public void insertByEntity(WeixinAuthorizationToken weixinAuthorizationToken);
	public void deleteByEntity(Map<String,Object> map);
	public void updateByEntity(Map<String,Object> map);
	public void updateByEntity(WeixinAuthorizationToken weixinAuthorizationToken);
	public List<WeixinAuthorizationToken> selectByObject (Map<String,Object> map);
	public WeixinAuthorizationToken selectSingle(Map<String,Object> map);
	public WeixinAuthorizationToken selectSingle(WeixinAuthorizationToken weixinAuthorizationToken);
}