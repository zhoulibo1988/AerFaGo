package com.ivan.dubbo.service.impl.weixin;

import java.util.Map;

import org.ivan.api.WeixinAuthCodeService;
import org.ivan.entity.WeixinAuthCode;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinAuthCodeMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthCodeServiceImpl implements WeixinAuthCodeService {
	// 注入当前dao对象
    @Autowired
    private WeixinAuthCodeMapper weixinAuthCodeMapper;

	public void insert(WeixinAuthCode weixinAuthCode) {
		weixinAuthCodeMapper.insertByEntity(weixinAuthCode);
		
	}

	public WeixinAuthCode selectSingle(Map<String, Object> map) {
		return weixinAuthCodeMapper.selectSingle(map);
	}

	public WeixinAuthCode selectSingle(WeixinAuthCode weixinAuthCode) {
		// TODO Auto-generated method stub
		return weixinAuthCodeMapper.selectSingle(weixinAuthCode);
	}

	public void updateByEntity(WeixinAuthCode weixinAuthCode) {
		// TODO Auto-generated method stub
		weixinAuthCodeMapper.updateByEntity(weixinAuthCode);
	}
}
