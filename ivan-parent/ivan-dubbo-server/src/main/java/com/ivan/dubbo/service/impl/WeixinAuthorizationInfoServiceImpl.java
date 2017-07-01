package com.ivan.dubbo.service.impl;

import java.util.Map;

import org.ivan.api.WeixinAuthorizationInfoService;
import org.ivan.entity.WeixinAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.WeixinAuthorizationInfoMapper;


/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthorizationInfoServiceImpl implements WeixinAuthorizationInfoService {
	// 注入当前dao对象
    @Autowired
    private WeixinAuthorizationInfoMapper weixinAuthorizationInfoMapper;

	public WeixinAuthorizationInfo selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinAuthorizationInfoMapper.selectSingle(map);
	}

	public WeixinAuthorizationInfo selectSingle(
			WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		// TODO Auto-generated method stub
		return weixinAuthorizationInfoMapper.selectSingle(WeixinAuthorizationInfo);
	}

	public void updateByEntity(WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		// TODO Auto-generated method stub
		weixinAuthorizationInfoMapper.updateByEntity(WeixinAuthorizationInfo);
	}

	public void insert(WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		weixinAuthorizationInfoMapper.insertByEntity(WeixinAuthorizationInfo);
	}
    
 
}
