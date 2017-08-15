package com.ivan.dubbo.service.impl.weixin;

import java.util.Map;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.WeixinAuthCode;
import org.ivan.entity.WeixinAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinAuthorizationTokenMapper;
/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthorizationTokenServiceImpl implements WeixinAuthorizationTokenService {
	@Autowired
	private WeixinAuthorizationTokenMapper weixinAuthorizationTokenMapper;
	
	public void insert(WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		weixinAuthorizationTokenMapper.insertByEntity(weixinAuthorizationToken);
		
	}

	public WeixinAuthorizationToken selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinAuthorizationTokenMapper.selectSingle(map);
	}

	public WeixinAuthorizationToken selectSingle(
			WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		return weixinAuthorizationTokenMapper.selectSingle(weixinAuthorizationToken);
	}

	public void updateByEntity(WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		weixinAuthorizationTokenMapper.updateByEntity(weixinAuthorizationToken);
	}
 
}
